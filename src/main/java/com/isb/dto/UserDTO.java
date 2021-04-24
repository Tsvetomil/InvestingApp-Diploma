package com.isb.dto;

import com.isb.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String user;
    private String email;

    UserDTO(){
    }

    public static UserDTO toDTO(User user){
        UserDTO userCopy = new UserDTO();
        userCopy.setId(user.getId());
        userCopy.setUser(user.getUser());
        userCopy.setEmail(user.getEmail());

        return userCopy;
    }
}
