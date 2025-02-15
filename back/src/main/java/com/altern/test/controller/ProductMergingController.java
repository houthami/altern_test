package com.altern.test.controller;

import com.altern.test.dto.ProductRequest;
import com.altern.test.dto.ProductResponse;
import com.altern.test.service.IProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{id}")
@Slf4j
public class ProductMergingController {

    @Autowired
    private IProductService productService;

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.updateProduct(id, request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
