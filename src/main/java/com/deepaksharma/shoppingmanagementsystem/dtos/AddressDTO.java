package com.deepaksharma.shoppingmanagementsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {
    String street;

    String city;

    String state;

    String country;

    @NotBlank(message = "Zip code is mandatory")
    String zipCode;
}
