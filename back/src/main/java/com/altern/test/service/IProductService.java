package com.altern.test.service;

import com.altern.test.dto.ProductRequest;
import com.altern.test.dto.ProductResponse;
import com.altern.test.entity.Product;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IProductService {
    @Transactional
    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest productDetails);

    void deleteProduct(Long id);
}
