package com.deepaksharma.shoppingmanagementsystem.service.order;

import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.model.Order;
import com.deepaksharma.shoppingmanagementsystem.repository.OrderItemRepository;
import com.deepaksharma.shoppingmanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void cancelOrder(Long id) {

    }

    @Override
    public String checkOrderStatus(Long id) {
        Order order = findOrderById(id);
        return order.getOrderStatus().toString();
    }
}
