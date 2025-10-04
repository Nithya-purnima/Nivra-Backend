package com.example.nivra.controller;

import com.example.nivra.entity.Product;
import com.example.nivra.entity.Seller;
import com.example.nivra.repository.ProductRepository;
import com.example.nivra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private FileStorageService fileService;

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            Product product = new Product();
            product.setTitle(title);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);

            if (image != null && !image.isEmpty()) {
                String filename = fileService.saveFile(image); // returns only filename
                product.setImagePath(filename);
            }

            productRepo.save(product);
            return ResponseEntity.ok("Product added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add product: " + e.getMessage());
        }
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }

}