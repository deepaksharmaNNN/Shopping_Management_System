package com.deepaksharma.shoppingmanagementsystem.dtos;

import com.deepaksharma.shoppingmanagementsystem.model.OrderItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    Long userId;
    List<OrderItem> orderItems;
    Double totalPrice;
}
