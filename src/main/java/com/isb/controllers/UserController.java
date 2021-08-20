package com.isb.controllers;

import com.isb.dto.UserDTO;
import com.isb.exception.UserAuthException;
import com.isb.exception.UserException;
import com.isb.model.ConfirmationToken;
import com.isb.model.User;
import com.isb.repository.ConfTokenRepository;
import com.isb.repository.UserRepository;
import com.isb.rest.utils.Response;
import com.isb.utils.Properties;
import com.isb.utils.UserUtils;
import com.isb.utils.mail.MailSender;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController implements IController{

    private static final String VERIFICATION_URL = "http://localhost:8080/confirm-account-code=";

    @Autowired
    private UserRepository usrRep;

    @Autowired
    private ConfTokenRepository tokenRep;

    Argon2 argon2 = Argon2Factory.create();

    @PostMapping("/register")
    public Response register(@RequestBody User user) throws UserException {
        UserUtils.verifyReg(user, usrRep);
        user.setPassword(argon2.hash(10,65536, 1, user.getPassword()));
        usrRep.save(user);

        return new Response(HttpStatus.OK.value(), UserDTO.toDTO(user));
    }

    @PostMapping("/login")
    public Response login(@RequestBody User user, HttpSession session) throws UserAuthException {
        User dbUser = usrRep.getByEmail(user.getEmail());
        if(null != dbUser && argon2.verify(dbUser.getPassword(), user.getPassword().getBytes(StandardCharsets.UTF_8))) {
            UserDTO userDTO = UserDTO.toDTO(dbUser);
            session.setAttribute("user", userDTO);
            dbUser.setLastLogin(LocalDateTime.now());
            usrRep.save(dbUser);
            return new Response(HttpStatus.OK.value(), UserDTO.toDTO(user));
        }
        else{
            throw new UserAuthException(Properties.getString("WRONG_CREDS"));
        }
    }

    @PostMapping("/logout")
    public Response logout(HttpSession session){
        session.invalidate();
        return new Response(HttpStatus.OK.value(), Properties.getString("LOGOUT_SUCCESSFUL"));
    }

    @GetMapping("/isAuthorized")
    public Response isAuthorized(HttpSession session){
        if(session.getAttribute("user") != null){
            return new Response(HttpStatus.OK.value());
        }
        return new Response(HttpStatus.UNAUTHORIZED.value());
    }

    @GetMapping("/isAdmin")
    public Response isAdmin(HttpSession session){
        UserDTO user = null;
        try {
            user = UserUtils.getUser(session);
        } catch (UserException e) {
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }
        return new Response(HttpStatus.OK.value(), user.isAdmin());

    }

    @PostMapping("/forgotten-password")
    public Response changePassword(@RequestHeader(name="Origin", required=false) final String host, @RequestBody User user){
        assert user.getEmail() != null;
        User usr = usrRep.getByEmail(user.getEmail());
        ConfirmationToken confirmationToken = new ConfirmationToken(usr.getId());
        try {
            MailSender.sendEmail(user.getEmail(), "Change password", "Please use the following link to change your password: " + constructURL(host, confirmationToken.getCode()));
            tokenRep.save(confirmationToken);
        } catch (MessagingException e) {
            logger.warn(e);
            return new Response(HttpStatus.BAD_REQUEST.value());
        }
        return new Response(HttpStatus.OK.value());

    }

    private String constructURL(String host, String code) {
        return host + "/password-reset?verification-code=" + code;
    }

    @PostMapping("/change-password")
    public Response resetPassword(@RequestBody User user, @RequestParam(name = "verification-code") String confirmationToken){
        ConfirmationToken confirmationTokenByCode = tokenRep.getConfirmationTokenByCode(confirmationToken);

        assert confirmationTokenByCode != null;
        User userById = usrRep.getUserById(confirmationTokenByCode.getUserID());

        assert userById != null;

        userById.setPassword(argon2.hash(10,65536, 1, user.getPassword()));

        usrRep.save(userById);

        tokenRep.delete(confirmationTokenByCode);

        return new Response(HttpStatus.OK.value());
    }

    @GetMapping("/user")
    public Response getUser(HttpSession session){
        UserDTO user = null;
        try {
            user = UserUtils.getUser(session);
        } catch (UserException e) {
            logger.warn(e);
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }

        return new Response(HttpStatus.OK.value(), user);
    }

    @PostMapping("/change-email")
    public Response changeEmail(@RequestBody User user, HttpSession session){
        UserDTO userFromSession = null;
        try {
            userFromSession = UserUtils.getUser(session);
        } catch (UserException e) {
            logger.warn(e);
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }
        User userById = usrRep.getUserById(userFromSession.getId());

        boolean verify = argon2.verify(userById.getPassword(), user.getPassword().getBytes(StandardCharsets.UTF_8));

        if(!verify){
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }
        userById.setEmail(user.getEmail());
        usrRep.save(userById);

        return new Response(HttpStatus.OK.value());
    }

    @PostMapping("/user/change-pass")
    public Response changePassword(@RequestBody Map<String, String> params, HttpSession session){
        String currPassword = params.get("currPassword");
        String newPassword = params.get("newPassword");
        UserDTO userFromSession = null;
        try {
            userFromSession = UserUtils.getUser(session);
        } catch (UserException e) {
            logger.warn(e);
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }

        User userById = usrRep.getUserById(userFromSession.getId());

        boolean verify = argon2.verify(userById.getPassword(), currPassword.getBytes(StandardCharsets.UTF_8));

        if(!verify){
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }

        userById.setPassword(argon2.hash(10,65536, 1, newPassword));

        usrRep.save(userById);

        return new Response(HttpStatus.OK.value());
    }

    @GetMapping("/all")
    public Response getAll(HttpSession session){
        try {
            UserDTO user = UserUtils.getUser(session);
            User userById = usrRep.getUserById(user.getId());
            if(!userById.isAdmin()){
                throw new UserException("Unauthorized");
            }
            List<UserDTO> result = new ArrayList<>();
            for (User usr : usrRep.findAll()){
                result.add(UserDTO.toDTO(usr));
            }
            return new Response(HttpStatus.OK.value(), result);
        } catch (UserException e) {
            return new Response(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
