package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.dtos.UserDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add/user") // http://localhost:8080/api/v1/user/add/user
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse("User added successfully", userService.saveUser(userDTO)));
        } catch (FailedToSaveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/add/admin") // http://localhost:8080/api/v1/user/add/admin
    public ResponseEntity<ApiResponse> addAdmin(@RequestBody @Valid UserDTO userDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse("Admin added successfully", userService.saveAdmin(userDTO)));
        } catch (FailedToSaveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all") // http://localhost:8080/api/v1/user/all
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            return ResponseEntity.ok(new ApiResponse("All users fetched successfully", userService.findAll()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}") // http://localhost:8080/api/v1/user/delete/1
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/user") // http://localhost:8080/api/v1/user/update/user
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UserDTO userDTO, @RequestParam String email) {
        try {
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userService.updateUser(userDTO, email)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
