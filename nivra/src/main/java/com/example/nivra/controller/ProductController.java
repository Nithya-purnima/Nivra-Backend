package com.example.nivra.controller;


import com.example.nivra.entity.Product;
import com.example.nivra.entity.Seller;
import com.example.nivra.repository.ProductRepository;
import com.example.nivra.repository.SellerRepository;
import com.example.nivra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired private ProductRepository productRepo;
    @Autowired private SellerRepository sellerRepo;
    @Autowired private FileStorageService fileService;

    @PostMapping
    public String addProduct(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam MultipartFile image,
            @RequestParam boolean ngoDonation,
            @RequestParam Long sellerId // pass sellerId from frontend
    ) throws Exception {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));

        Product p = new Product();
        p.setTitle(title);
        p.setDescription(description);
        p.setCategory(category);
        p.setPrice(price);
        p.setNgoDonation(ngoDonation);
        p.setSeller(seller);
        p.setImagePath(fileService.saveFile(image));

        productRepo.save(p);
        return "Product added";
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}

