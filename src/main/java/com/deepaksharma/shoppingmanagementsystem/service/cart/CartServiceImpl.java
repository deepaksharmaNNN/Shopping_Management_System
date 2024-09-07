package com.deepaksharma.shoppingmanagementsystem.service.cart;

import com.deepaksharma.shoppingmanagementsystem.dtos.CartItemDTO;
import com.deepaksharma.shoppingmanagementsystem.exceptions.FailedToSaveException;
import com.deepaksharma.shoppingmanagementsystem.exceptions.ResourceNotFoundException;
import com.deepaksharma.shoppingmanagementsystem.model.Cart;
import com.deepaksharma.shoppingmanagementsystem.model.CartItem;
import com.deepaksharma.shoppingmanagementsystem.model.Product;
import com.deepaksharma.shoppingmanagementsystem.model.User;
import com.deepaksharma.shoppingmanagementsystem.repository.CartItemRepository;
import com.deepaksharma.shoppingmanagementsystem.repository.CartRepository;
import com.deepaksharma.shoppingmanagementsystem.service.Product.ProductService;
import com.deepaksharma.shoppingmanagementsystem.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(Long userId) {
        User user = userService.findUserById(userId);
        // Create and initialize the cart
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());

        // Save the cart and return
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found for user with id: " + userId));
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public CartItem addCartItem(Long userId, CartItemDTO cartItemDTO) {
        // Fetch the user's cart
        Cart cart = getCart(userId);

        // Fetch the product based on the product ID from the DTO
        Product product = productService.findProductById(cartItemDTO.getProductId());

        // Check if the requested quantity is available
        if (product.getQuantity() < cartItemDTO.getQuantity()) {
            throw new ResourceNotFoundException("Product with id: " + product.getId() + " has insufficient quantity");
        }

        // Check if the cart already contains this product
        CartItem existingCartItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()))
            .findFirst()
            .orElse(null);

        if (existingCartItem != null) {
            // If the product is already in the cart, update the quantity and price
            cart.setTotalPrice(cart.getTotalPrice() - existingCartItem.getPrice()); // Subtract old price
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDTO.getQuantity());
            existingCartItem.setPrice(existingCartItem.getQuantity() * product.getPrice());
            cart.setTotalPrice(cart.getTotalPrice() + existingCartItem.getPrice()); // Add new price
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item if it doesn't exist
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(cartItemDTO.getQuantity() * product.getPrice());
            cart.getCartItems().add(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
            cartItemRepository.save(cartItem);
        }

        // Save the updated cart
        cartRepository.save(cart);

        return cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()))
            .findFirst()
            .orElse(null);
    }

    @Transactional
    @Override
    public CartItem updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);

        // Check for negative quantity
        if (quantity < 0) {
            throw new FailedToSaveException("Quantity cannot be negative");
        }

        Product product = productService.findProductById(productId);

        // Check for sufficient stock
        if (product.getQuantity() < quantity) {
            throw new FailedToSaveException(product.getName() + " has insufficient quantity");
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        // Update cart item quantity and price
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice() * quantity);
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
        cartItemRepository.save(cartItem);

        // Save the updated cart
        cartRepository.save(cart);

        return cartItem;
    }

    @Transactional
    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cart = getCart(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found for id: " + cartItemId));

        // Remove the cart item and update the total price
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        // Save the updated cart
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);

        // Save the updated cart
        cartRepository.save(cart);
    }
}
