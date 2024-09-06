package com.deepaksharma.shoppingmanagementsystem.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDTO {
    Long userId;
    List<CartItemDTO> cartItems;
    Double totalPrice;
}
