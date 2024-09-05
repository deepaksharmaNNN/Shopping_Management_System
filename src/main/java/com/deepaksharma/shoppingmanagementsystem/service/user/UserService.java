package com.deepaksharma.shoppingmanagementsystem.service.user;

import com.deepaksharma.shoppingmanagementsystem.dtos.UserDTO;
import com.deepaksharma.shoppingmanagementsystem.model.User;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO user);
    User saveAdmin(UserDTO user);
    User findUserById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    User updateUser(UserDTO user, String email);
    void deleteUser(Long id);
}
