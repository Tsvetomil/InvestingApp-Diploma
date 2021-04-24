package com.isb.dto;

import com.isb.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    UserDTO(){
    }

    public static UserDTO toDTO(User user){
        UserDTO userCopy = new UserDTO();
        userCopy.setId(user.getId());
        userCopy.setFirstName(user.getFirstName());
        userCopy.setLastName(user.getLastName());
        userCopy.setEmail(user.getEmail());

        return userCopy;
    }
}
