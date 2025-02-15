package com.altern.test.repository;

import com.altern.test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
}
