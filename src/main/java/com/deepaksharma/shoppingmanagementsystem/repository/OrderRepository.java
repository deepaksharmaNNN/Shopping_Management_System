package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
