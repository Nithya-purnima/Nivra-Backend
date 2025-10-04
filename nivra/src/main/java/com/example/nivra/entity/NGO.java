package com.example.nivra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NGO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgName;
    private String email;
    private String phone;
    private String address;
    private String password;

    private String certificatePath; // verification certificate

}
