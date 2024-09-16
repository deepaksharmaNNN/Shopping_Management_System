package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.dtos.OrderDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/order")
public class OrderController {

    private final OrderService orderService;

    // Place order
    @PostMapping("/place") // http://localhost:8080/api/v1/order/place
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse("Order placed successfully", orderService.createOrder(orderDTO)));
        } catch (FailedToSaveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Find order by id
    @PostMapping("/find") // http://localhost:8080/api/v1/order/find
    public ResponseEntity<ApiResponse> findOrder(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(new ApiResponse("Order found", orderService.findOrderById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Find all orders
    @GetMapping("/all") // http://localhost:8080/api/v1/order/all
    public ResponseEntity<ApiResponse> findAllOrders() {
        try {
            return ResponseEntity.ok(new ApiResponse("All orders", orderService.findAllOrders()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Cancel order
    @PostMapping("/cancel") // http://localhost:8080/api/v1/order/cancel
    public ResponseEntity<ApiResponse> cancelOrder(@RequestParam Long id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok(new ApiResponse("Order cancelled successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get order status
    @PostMapping("/status") // http://localhost:8080/api/v1/order/status
    public ResponseEntity<ApiResponse> getOrderStatus(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(new ApiResponse("Order status", orderService.checkOrderStatus(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
