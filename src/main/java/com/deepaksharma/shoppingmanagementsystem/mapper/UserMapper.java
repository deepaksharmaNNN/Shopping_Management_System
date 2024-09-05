package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.dtos.UserDTO;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import lombok.Builder;
import lombok.experimental.UtilityClass;

@UtilityClass
@Builder
public class UserMapper {
    public User mapToUser(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();

    }
}
