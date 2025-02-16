package com.altern.test.controller;

import com.altern.test.dto.ProductRequest;
import com.altern.test.dto.ProductResponse;
import com.altern.test.service.IProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Creating product with code: {}", request.getCode());
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

}
