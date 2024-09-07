package com.deepaksharma.shoppingmanagementsystem.mapper;

import com.deepaksharma.shoppingmanagementsystem.model.Order;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import com.deepaksharma.shoppingmanagementsystem.response.OrderResponse;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;


@UtilityClass
public class OrderMapper {
    public OrderResponse mapOrderToOrderResponse(Order order) {
        User user = order.getUser();
        //Map User to UserSummary
        OrderResponse.UserSummary userSummary = OrderResponse.UserSummary.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .address(OrderResponse.UserSummary.AddressDTO.builder()
                        .street(user.getAddress().getStreet())
                        .city(user.getAddress().getCity())
                        .state(user.getAddress().getState())
                        .country(user.getAddress().getCountry())
                        .zipCode(user.getAddress().getZipCode())
                        .build())
                .build();

        //Map order items
        var orderItems = order.getOrderItems().stream()
                .map(orderItem -> OrderResponse.OrderItemDTO.builder()
                        .productId(orderItem.getProduct().getId())
                        .productName(orderItem.getProduct().getName())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build())
                .toList();

        //Map Order to OrderResponse
        return OrderResponse.builder()
                .id(order.getId())
                .user(userSummary)
                .orderItems(orderItems)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus().name())
                .build();
    }
}
