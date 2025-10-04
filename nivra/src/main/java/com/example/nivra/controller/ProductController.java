//package com.example.nivra.controller;
//
//import com.example.nivra.entity.*;
//import com.example.nivra.repository.*;
//import com.example.nivra.service.FileStorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/products")
//@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
//public class ProductController {
//
//    @Autowired private ProductRepository productRepo;
//    @Autowired private SellerRepository sellerRepo;
//    @Autowired private ConsumerRepository consumerRepo;
//    @Autowired private CartItemRepository cartItemRepo;
//    @Autowired private FileStorageService fileService;
//
//    // ✅ Add a product
//    @PostMapping
//    public ResponseEntity<?> addProduct(
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam Double price,
//            @RequestParam Integer quantity,
//            @RequestParam Long sellerId,
//            @RequestParam(required = false) MultipartFile image
//    ) {
//        try {
//            Seller seller = sellerRepo.findById(sellerId)
//                    .orElseThrow(() -> new RuntimeException("Seller not found"));
//
//            Product product = new Product();
//            product.setTitle(name);
//            product.setDescription(description);
//            product.setPrice(price);
//            product.setQuantity(quantity);
//            product.setSeller(seller);
//
//            if (image != null && !image.isEmpty()) {
//                String imagePath = fileService.saveFile(image);
//                product.setImagePath(imagePath);
//            }
//
//            productRepo.save(product);
//            return ResponseEntity.ok("Product added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Failed to add product: " + e.getMessage());
//        }
//    }
//
//    // ✅ Get all products
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productRepo.findAll());
//    }
//
//    // ✅ Get product by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Long id) {
//        try {
//            Product product = productRepo.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//            return ResponseEntity.ok(product);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//
//    // ✅ Get all products by Seller
//    @GetMapping("/seller/{sellerId}")
//    public ResponseEntity<List<Product>> getSellerProducts(@PathVariable Long sellerId) {
//        return ResponseEntity.ok(productRepo.findBySellerId(sellerId));
//    }
//
//    // ✅ Add product to Wishlist
//    @PostMapping("/{productId}/wishlist")
//    public ResponseEntity<?> addToWishlist(
//            @PathVariable Long productId,
//            @RequestParam Long userId
//    ) {
//        try {
//            Consumer consumer = consumerRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
//            Product product = productRepo.findById(productId)
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            consumer.getWishlist().add(product);
//            consumerRepo.save(consumer);
//
//            return ResponseEntity.ok("Added to wishlist");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//
//    // ✅ Add product to Cart
//    @PostMapping("/{productId}/cart")
//    public ResponseEntity<?> addToCart(
//            @PathVariable Long productId,
//            @RequestParam Long userId,
//            @RequestParam Integer quantity
//    ) {
//        try {
//            Consumer consumer = consumerRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
//            Product product = productRepo.findById(productId)
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            if (product.getQuantity() < quantity) {
//                return ResponseEntity.badRequest().body("Not enough stock available");
//            }
//
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setConsumer(consumer);
//
//            cartItemRepo.save(cartItem);
//
//            return ResponseEntity.ok("Added to cart");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//
//    // ✅ Remove product from Wishlist
//    @DeleteMapping("/{productId}/wishlist")
//    public ResponseEntity<?> removeFromWishlist(
//            @PathVariable Long productId,
//            @RequestParam Long userId
//    ) {
//        try {
//            Consumer consumer = consumerRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
//            Product product = productRepo.findById(productId)
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            consumer.getWishlist().remove(product);
//            consumerRepo.save(consumer);
//
//            return ResponseEntity.ok("Removed from wishlist");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//}



package com.example.nivra.controller;

import com.example.nivra.entity.*;
import com.example.nivra.repository.*;
import com.example.nivra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {

    @Autowired private ProductRepository productRepo;
    @Autowired private SellerRepository sellerRepo;
    @Autowired private ConsumerRepository consumerRepo;
    @Autowired private CartItemRepository cartItemRepo;
    @Autowired private ProductRequestRepository productRequestRepo; // New
    @Autowired private FileStorageService fileService;

    // ✅ Add a product
    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam Long sellerId,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            Seller seller = sellerRepo.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("Seller not found"));

            Product product = new Product();
            product.setTitle(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setSeller(seller);

            if (image != null && !image.isEmpty()) {
                String imagePath = fileService.saveFile(image);
                product.setImagePath(imagePath);
            }

            productRepo.save(product);
            return ResponseEntity.ok("Product added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add product: " + e.getMessage());
        }
    }

    // ✅ Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }

    // ✅ Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Get all products by Seller
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getSellerProducts(@PathVariable Long sellerId) {
        return ResponseEntity.ok(productRepo.findBySellerId(sellerId));
    }

    // ✅ Add product to Wishlist
    @PostMapping("/{productId}/wishlist")
    public ResponseEntity<?> addToWishlist(
            @PathVariable Long productId,
            @RequestParam Long userId
    ) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            consumer.getWishlist().add(product);
            consumerRepo.save(consumer);

            return ResponseEntity.ok("Added to wishlist");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Remove product from Wishlist
    @DeleteMapping("/{productId}/wishlist")
    public ResponseEntity<?> removeFromWishlist(
            @PathVariable Long productId,
            @RequestParam Long userId
    ) {
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

    // ✅ Add product to Cart
    @PostMapping("/{productId}/cart")
    public ResponseEntity<?> addToCart(
            @PathVariable Long productId,
            @RequestParam Long userId,
            @RequestParam Integer quantity
    ) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < quantity) {
                return ResponseEntity.badRequest().body("Not enough stock available");
            }

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setConsumer(consumer);

            cartItemRepo.save(cartItem);

            return ResponseEntity.ok("Added to cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Request a product
    @PostMapping("/{productId}/request")
    public ResponseEntity<?> requestProduct(
            @PathVariable Long productId,
            @RequestParam Long userId
    ) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductRequest request = new ProductRequest();
            request.setConsumer(consumer);
            request.setProduct(product);

            productRequestRepo.save(request);

            return ResponseEntity.ok("Request sent to seller!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Get Cart items for a consumer
    @GetMapping("/cart")
    public ResponseEntity<?> getCartItems(@RequestParam Long userId) {
        try {
            List<CartItem> cartItems = cartItemRepo.findByConsumerId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Get Wishlist items for a consumer
    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlistItems(@RequestParam Long userId) {
        try {
            Consumer consumer = consumerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Consumer not found"));
            return ResponseEntity.ok(consumer.getWishlist());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Get Requested products for a consumer
    @GetMapping("/requests")
    public ResponseEntity<?> getRequests(@RequestParam Long userId) {
        try {
            List<ProductRequest> requests = productRequestRepo.findByConsumerId(userId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
