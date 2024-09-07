package com.deepaksharma.shoppingmanagementsystem.service.order;

import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.model.Order;
import com.deepaksharma.shoppingmanagementsystem.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderDTO orderDTO);

    OrderResponse findOrderById(Long id);

    List<OrderResponse> findAllOrders();

    void cancelOrder(Long id);

    String checkOrderStatus(Long id);
}