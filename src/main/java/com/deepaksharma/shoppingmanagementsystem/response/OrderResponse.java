package com.deepaksharma.shoppingmanagementsystem.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    UserSummary user;
    List<OrderItemDTO> orderItems;
    Double totalPrice;
    String orderStatus;

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserSummary {
        String username;
        String email;
        AddressDTO address;

        @Data
        @Builder
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class AddressDTO {
            String street;
            String city;
            String state;
            String country;
            String zipCode;
        }
    }

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderItemDTO {
        Long productId;
        String productName;
        Integer quantity;
        Double price;
    }
}