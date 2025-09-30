package com.example.nivra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nivra.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
