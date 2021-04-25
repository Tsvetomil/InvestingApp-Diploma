package com.isb.utils;

import com.isb.dto.UserDTO;
import com.isb.exception.UserException;
import com.isb.model.User;
import com.isb.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

public class UserUtils {

    private static final Pattern VALID_PASS_REGEX= Pattern.compile(
            "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$"
    );
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private UserUtils(){
    }

    public static void verifyReg(User userToVerify, UserRepository userRepository) throws UserException{
//        if(userRepository.getByEmail(userToVerify.getEmail()) != null){
//            throw new UserException(Properties.getString("EMAIL_EXIST"));
//        }
//        if(!VALID_PASS_REGEX.matcher(userToVerify.getPassword()).matches()){
//            throw new UserException(Properties.getString("WEAK_PASS"));
//        }
//        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(userToVerify.getEmail()).matches()){
//            throw new UserException(Properties.getString("INVALID_EMAIL"));
//        }
    }

    public static UserDTO getUser(HttpSession session) throws UserException {
        UserDTO userSession = (UserDTO) session.getAttribute("user");
        if(userSession != null){
            return userSession;
        }
        throw new UserException(Properties.getString("NOT_LOGGED_IN"));
    }

}
