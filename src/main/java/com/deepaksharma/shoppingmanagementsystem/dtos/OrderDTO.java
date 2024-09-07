package com.deepaksharma.shoppingmanagementsystem.dtos;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderDTO {
    Long userId;
    List<CartItemDTO> cartItems;
}
