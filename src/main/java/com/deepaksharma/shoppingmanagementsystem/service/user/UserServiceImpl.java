package com.deepaksharma.shoppingmanagementsystem.service.user;

import com.deepaksharma.shoppingmanagementsystem.dtos.UserDTO;
import com.deepaksharma.shoppingmanagementsystem.enums.Role;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.mapper.AddressMapper;
import com.deepaksharma.shoppingmanagementsystem.mapper.UserMapper;
import com.deepaksharma.shoppingmanagementsystem.model.Address;
import com.deepaksharma.shoppingmanagementsystem.model.Cart;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import com.deepaksharma.shoppingmanagementsystem.repository.UserRepository;
import com.deepaksharma.shoppingmanagementsystem.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;


    @Override
    public User saveUser(UserDTO user) {
        User newUser = UserMapper.mapToUser(user);
        newUser.setRole(Role.USER);
        Address address = AddressMapper.mapToAddress(user.getAddress());
        newUser.setAddress(address);

        User savedUser = userRepository.save(newUser);
        Cart cart = cartService.createCart(savedUser.getId());
        savedUser.setCart(cart);
        return userRepository.save(savedUser);
    }

    @Override
    public User saveAdmin(UserDTO user) {
        User newUser = UserMapper.mapToUser(user);
        newUser.setRole(Role.ADMIN);
        Address address = AddressMapper.mapToAddress(user.getAddress());
        newUser.setAddress(address);
        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDTO user, String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        // Check if the new email is already in use and is different from the current email
        if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.findByEmail(user.getEmail()) != null) {
            throw new FailedToSaveException("User with email: " + user.getEmail() + " already exists");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        if (user.getAddress() != null) {
            existingUser.setAddress(AddressMapper.mapToAddress(user.getAddress()));
        }

        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
