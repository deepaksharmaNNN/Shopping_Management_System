package com.deepaksharma.shoppingmanagementsystem.service.cart;

import com.deepaksharma.shoppingmanagementsystem.dtos.CartItemDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Cart;
import com.deepaksharma.shoppingmanagementsystem.model.CartItem;

public interface CartService {
    Cart createCart(Long userId);
    Cart getCart(Long userId);
    void saveCart(Cart cart);
    CartItem addCartItem(Long userId, CartItemDTO cartItemDTO);
    CartItem updateCartItemQuantity(Long userId, Long productId, Integer quantity);
    void removeCartItem(Long userId, Long cartItemId);
    void clearCart(Long userId);
    Double getTotalAmount(Long userId);
}
