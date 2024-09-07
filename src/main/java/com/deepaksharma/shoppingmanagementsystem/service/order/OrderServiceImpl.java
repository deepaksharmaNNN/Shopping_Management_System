package com.deepaksharma.shoppingmanagementsystem.service.order;


import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.enums.OrderStatus;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.mapper.OrderMapper;
import com.deepaksharma.shoppingmanagementsystem.model.*;
import com.deepaksharma.shoppingmanagementsystem.repository.OrderRepository;
import com.deepaksharma.shoppingmanagementsystem.response.OrderResponse;
import com.deepaksharma.shoppingmanagementsystem.service.Product.ProductService;
import com.deepaksharma.shoppingmanagementsystem.service.cart.CartService;
import com.deepaksharma.shoppingmanagementsystem.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Value("${delivery.charge}")
    private Double deliveryCharge;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        User user = userService.findUserById(orderDTO.getUserId());
        Cart cart = user.getCart();

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        // Check for insufficient stock for any product
        cart.getCartItems().forEach(cartItem -> {
            Product product = productService.findProductById(cartItem.getProduct().getId());
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new IllegalStateException("Product " + product.getName() + " has insufficient stock");
            }
        });

        // Create a new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.ACCEPTED);

        // Create and add order items, update product quantities
        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            Product product = productService.findProductById(cartItem.getProduct().getId());

            // Reduce the product quantity by the cart item's quantity
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productService.save(product); // Update the product in the database

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice() * cartItem.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Calculate total price
        Double totalPrice = cart.getTotalPrice();
        order.setTotalPrice(calculateTotalPrice(totalPrice));

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after placing the order
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartService.saveCart(cart);

        return OrderMapper.mapOrderToOrderResponse(savedOrder);
    }

    public Double calculateTotalPrice(Double totalPrice) {
        return totalPrice > 500 ? totalPrice : totalPrice + deliveryCharge;
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + id));
        return OrderMapper.mapOrderToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::mapOrderToOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        // Fetch order from the repository
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + id));

        // Check if the order is already delivered
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order is already delivered, can't cancel");
        }

        // Check if the order is already canceled
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already canceled");
        }

        // If the order is in ACCEPTED status, proceed with cancellation
        if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            // Restore the product quantities
            order.getOrderItems().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productService.save(product); // Update the product in the database
            });

            // Update the order status to be CANCELLED
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order can't be canceled");
        }
    }

    @Override
    public String checkOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return order != null ? order.getOrderStatus().name() : "Order not found";
    }
}
