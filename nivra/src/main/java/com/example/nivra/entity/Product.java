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

    private String title;       // instead of "name"
    private String category;    // add this field
    private String description;
    private Double price;
    private Integer quantity;

    @Column(name = "image_path")  // map image column properly
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
