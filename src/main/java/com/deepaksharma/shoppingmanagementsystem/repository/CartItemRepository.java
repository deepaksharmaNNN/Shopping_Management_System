package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
