package com.cm.converter;

import com.cm.dao.User;
import com.cm.dto.UserDTO;

public class UserConverter {
    public static UserDTO convertUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(user.getAge());
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNick_name(user.getNick_name());
        return userDTO;
    }

    public static User convertUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setAge(userDTO.getAge());
        user.setId(userDTO.getId());
        user.setEmail(user.getEmail());
        user.setNick_name(user.getNick_name());
        return user;
    }
}
