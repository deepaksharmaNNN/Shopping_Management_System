package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
