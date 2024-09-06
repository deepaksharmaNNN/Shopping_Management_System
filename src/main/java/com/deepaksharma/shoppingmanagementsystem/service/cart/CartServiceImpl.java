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


    @Transactional
    @Override
    public CartItem addCartItem(Long userId, CartItemDTO cartItemDTO) {
        Cart cart = getCart(userId);
        Product product = productService.findProductById(cartItemDTO.getProductId());
        if (product.getQuantity() < cartItemDTO.getQuantity()) {
            throw new ResourceNotFoundException("Product with id: " + product.getId() + " has insufficient quantity");
        }
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        product.setQuantity(product.getQuantity() - cartItemDTO.getQuantity());
        cartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);
        //check for insufficient quantity after updating
        if (quantity < 0) {
            throw new FailedToSaveException("Quantity cannot be negative");
        }
        Product product = productService.findProductById(productId);
        if(product.getQuantity() < quantity){
            throw new FailedToSaveException("Product with id: " + product.getId() + " has insufficient quantity");
        }
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found for product with id: " + productId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long productId) {
        Cart cart = getCart(userId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found for product with id: " + productId));
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }
}