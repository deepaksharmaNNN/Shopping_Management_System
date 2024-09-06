package com.deepaksharma.shoppingmanagementsystem.service.order;

import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.enums.OrderStatus;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.model.Cart;
import com.deepaksharma.shoppingmanagementsystem.model.Order;
import com.deepaksharma.shoppingmanagementsystem.model.OrderItem;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import com.deepaksharma.shoppingmanagementsystem.repository.OrderRepository;
import com.deepaksharma.shoppingmanagementsystem.service.cart.CartService;
import com.deepaksharma.shoppingmanagementsystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Value("${delivery.charge}")
    private Double deliveryCharge;

    @Transactional
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        User user = userService.findUserById(orderDTO.getUserId());
        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.ACCEPTED);
        Double finalPrice = calculateTotalPrice(orderDTO.getTotalPrice());
        order.setTotalPrice(finalPrice);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartService.saveCart(cart);

        return savedOrder;
    }

    public Double calculateTotalPrice(Double totalPrice) {
        return totalPrice > 500 ? totalPrice : totalPrice + deliveryCharge;
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

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + id));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order is already delivered, can't cancel");
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already canceled");
        }

        if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order can't be canceled");
        }
    }

    @Override
    public String checkOrderStatus(Long id) {
        Order order = findOrderById(id);
        return order.getOrderStatus().toString();
    }
}
