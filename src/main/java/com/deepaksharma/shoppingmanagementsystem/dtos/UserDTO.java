package com.deepaksharma.shoppingmanagementsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @NotBlank(message = "Username is mandatory")
    String username;

    @NotBlank(message = "Email is mandatory")
    String email;

    @NotBlank(message = "Password is mandatory")
    String password;

    AddressDTO address;
}
