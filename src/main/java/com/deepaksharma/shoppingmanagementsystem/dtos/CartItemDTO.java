package com.deepaksharma.shoppingmanagementsystem.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDTO {
    Long productId;
    String productName;
    Integer quantity;
    Double totalPrice;
}
