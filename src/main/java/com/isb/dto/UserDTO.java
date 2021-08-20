package com.isb.dto;

import com.isb.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String lastLogin;
    private String dateOfBirth;
    private boolean isAdmin;

    UserDTO(){
    }

    public static UserDTO toDTO(User user){
        UserDTO userCopy = new UserDTO();
        userCopy.setId(user.getId());
        userCopy.setFirstName(user.getFirstName());
        userCopy.setLastName(user.getLastName());
        userCopy.setEmail(user.getEmail());
        userCopy.setAdmin(user.isAdmin());
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            userCopy.setDateOfBirth(user.getDateOfBirth().format(format));
        } catch (NullPointerException e){
            //ignore
        }
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            userCopy.setLastLogin(user.getLastLogin().format(format));
        } catch (NullPointerException e){
            //ignore
        }
        return userCopy;
    }
}
