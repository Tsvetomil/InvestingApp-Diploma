package com.isb.controllers;

import com.isb.dto.UserDTO;
import com.isb.exception.UserAuthException;
import com.isb.exception.UserException;
import com.isb.model.User;
import com.isb.repository.UserRepository;
import com.isb.rest.utils.Response;
import com.isb.utils.Properties;
import com.isb.utils.UserUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api/users")
public class UserController implements IController{

    @Autowired
    private UserRepository usrRep;

    Argon2 argon2 = Argon2Factory.create();

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) throws UserException {
        UserUtils.verifyReg(user, usrRep);
        user.setPassword(argon2.hash(10,65536, 1, user.getPassword()));
        usrRep.save(user);

        return UserDTO.toDTO(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody User user, HttpSession session) throws UserAuthException {
        User dbUser = usrRep.getByUser(user.getUser());
        if(null != dbUser && argon2.verify(user.getPassword(), dbUser.getPassword().getBytes(StandardCharsets.UTF_8))) {
            UserDTO userDTO = UserDTO.toDTO(dbUser);
            session.setAttribute("user", userDTO);
            return UserDTO.toDTO(user);
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

    @GetMapping("/test")
    public String test(){
        return "That is an api test";
    }


}
