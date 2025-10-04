package com.example.nivra.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String certificatePath; // income certificate

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "consumer_wishlist",
            joinColumns = @JoinColumn(name = "consumer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlist = new HashSet<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CartItem> cartItems = new HashSet<>();

}

