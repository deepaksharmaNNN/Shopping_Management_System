package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.dtos.CartItemDTO;
import com.deepaksharma.shoppingmanagementsystem.model.CartItem;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartItemMapper {
    public CartItem mapToCartItem(CartItemDTO cartItemDto) {
        return CartItem.builder()
                .product(Product.builder().id(cartItemDto.getProductId()).build())
                .quantity(cartItemDto.getQuantity())
                .price(cartItemDto.getTotalPrice())
                .build();
    }
}
