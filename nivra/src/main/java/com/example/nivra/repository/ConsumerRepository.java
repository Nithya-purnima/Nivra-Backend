package com.example.nivra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nivra.entity.Consumer;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    Consumer findByEmail(String email);
}
