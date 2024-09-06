package com.deepaksharma.shoppingmanagementsystem.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDTO {
    Long userId;
    List<CartItemDTO> cartItems;
    Double totalPrice;
}
