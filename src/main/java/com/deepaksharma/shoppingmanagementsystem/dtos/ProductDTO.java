package com.deepaksharma.shoppingmanagementsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Brand is mandatory")
    String brand;

    @NotBlank(message = "Description is mandatory")
    String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be positive")
    Double price;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity should be positive")
    Integer quantity;

    @NotBlank(message = "Category is mandatory")
    String category;
}
