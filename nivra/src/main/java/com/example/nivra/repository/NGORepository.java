package com.example.nivra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nivra.entity.NGO;

public interface NGORepository extends JpaRepository<NGO, Long> {
    NGO findByEmail(String email);
}
