package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.dtos.CartDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Cart;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class CartMapper {
    public Cart mapToCart(CartDTO cartDto) {
        return Cart.builder()
                .user(User.builder().id(cartDto.getUserId()).build())
                .cartItems(cartDto.getCartItems().stream().map(CartItemMapper::mapToCartItem).collect(Collectors.toList()))
                .totalPrice(cartDto.getTotalPrice())
                .build();
    }
}
