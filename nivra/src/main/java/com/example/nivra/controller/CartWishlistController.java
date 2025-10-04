package com.example.nivra.controller;

import com.example.nivra.entity.CartItem;
import com.example.nivra.entity.Consumer;
import com.example.nivra.entity.Product;
import com.example.nivra.repository.CartItemRepository;
import com.example.nivra.repository.ConsumerRepository;
import com.example.nivra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartWishlistController {

    @Autowired private ConsumerRepository consumerRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CartItemRepository cartItemRepo;

    // ---------------- Wishlist ----------------

    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlist(@RequestParam Long userId) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            return ResponseEntity.ok(consumer.getWishlist());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/wishlist/remove/{productId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long productId,
                                                @RequestParam Long userId) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            consumer.getWishlist().remove(product);
            consumerRepo.save(consumer);
            return ResponseEntity.ok("Removed from wishlist");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ---------------- Cart ----------------

    @GetMapping("/cart")
    public ResponseEntity<?> getCartItems(@RequestParam Long userId) {
        try {
            List<CartItem> cartItems = cartItemRepo.findByConsumerId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/cart/add/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId,
                                       @RequestParam Long userId,
                                       @RequestParam(defaultValue = "1") Integer quantity) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < quantity)
                return ResponseEntity.badRequest().body("Not enough stock");

            // Check if item already exists in cart
            CartItem existingItem = cartItemRepo.findByConsumerId(userId).stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                int newQty = existingItem.getQuantity() + quantity;
                if (newQty > product.getQuantity())
                    return ResponseEntity.badRequest().body("Not enough stock");
                existingItem.setQuantity(newQty);
                cartItemRepo.save(existingItem);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setConsumer(consumer);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItemRepo.save(cartItem);
            }

            return ResponseEntity.ok("Product added to cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId,
                                            @RequestParam Long userId) {
        try {
            List<CartItem> items = cartItemRepo.findByConsumerId(userId);
            for (CartItem item : items) {
                if (item.getProduct().getId().equals(productId)) {
                    cartItemRepo.delete(item);
                    break;
                }
            }
            return ResponseEntity.ok("Product removed from cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/cart/update/{productId}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable Long productId,
                                                @RequestParam Long userId,
                                                @RequestBody CartItem updatedItem) {
        try {
            List<CartItem> items = cartItemRepo.findByConsumerId(userId);
            for (CartItem item : items) {
                if (item.getProduct().getId().equals(productId)) {
                    if (updatedItem.getQuantity() > item.getProduct().getQuantity()) {
                        return ResponseEntity.badRequest().body("Not enough stock");
                    }
                    item.setQuantity(updatedItem.getQuantity());
                    cartItemRepo.save(item);
                    return ResponseEntity.ok("Quantity updated");
                }
            }
            return ResponseEntity.badRequest().body("Item not found in cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
