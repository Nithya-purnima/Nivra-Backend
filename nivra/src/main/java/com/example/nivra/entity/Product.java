package com.example.nivra.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private double price;

    private String imagePath;

    private boolean ngoDonation;

    @ManyToOne
    private Seller seller;
}

