package com.deepaksharma.shoppingmanagementsystem.service.order;

import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findOrderById(Long id);

    List<Order> findAllOrders();

    void cancelOrder(Long id);

    String checkOrderStatus(Long id);
}