package com.deepaksharma.shoppingmanagementsystem.controller;

import com.deepaksharma.shoppingmanagementsystem.dtos.CartItemDTO;
import com.deepaksharma.shoppingmanagementsystem.response.ApiResponse;
import com.deepaksharma.shoppingmanagementsystem.service.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // create cart
    @PostMapping("/create/{userId}") // http://localhost:8080/api/v1/cart/create/1
    public ResponseEntity<ApiResponse> createCart(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Cart created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //get cart by user id
    @GetMapping("/get/{userId}") // http://localhost:8080/api/v1/cart/get/1
    public ResponseEntity<ApiResponse> getCartByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Cart fetched successfully", cartService.getCart(userId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // add item to cart
    @PostMapping("/add") // http://localhost:8080/api/v1/cart/add
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long userId, @RequestBody @Valid CartItemDTO cartItemDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", cartService.addCartItem(userId, cartItemDTO)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //update product quantity in cart
    @PutMapping("/update/quantity/{quantity}") // http://localhost:8080/api/v1/cart/update/quantity/2
    public ResponseEntity<ApiResponse> updateProductQuantityInCart(@RequestParam Long userId, @RequestParam Long productId, @PathVariable Integer quantity) {
        try {
            return ResponseEntity.ok(new ApiResponse("Product quantity updated successfully", cartService.updateCartItemQuantity(userId, productId, quantity)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //delete product from cart
    @DeleteMapping("/delete") // http://localhost:8080/api/v1/cart/delete
    public ResponseEntity<ApiResponse> deleteProductFromCart(@RequestParam Long userId, @RequestParam Long cartItemId) {
        try {
            cartService.removeCartItem(userId, cartItemId);
            return ResponseEntity.ok(new ApiResponse("Product deleted from cart successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/clear/{userId}") // http://localhost:8080/api/v1/cart/clear/1
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
