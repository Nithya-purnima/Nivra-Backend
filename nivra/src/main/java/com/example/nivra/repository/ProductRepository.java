package com.example.nivra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nivra.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

