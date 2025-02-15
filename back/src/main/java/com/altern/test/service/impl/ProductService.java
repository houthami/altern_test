package com.altern.test.service.impl;

import com.altern.test.dto.ProductRequest;
import com.altern.test.dto.ProductResponse;
import com.altern.test.entity.Product;
import com.altern.test.exception.DuplicateResourceException;
import com.altern.test.exception.ResourceNotFoundException;
import com.altern.test.mapper.GenericMapper;
import com.altern.test.repository.ProductRepository;
import com.altern.test.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final GenericMapper productMapper;

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        validateProductCode(request.getCode());

        Product product = productMapper.convert(request, Product.class);

        Product savedProduct = productRepository.save(product);
        return productMapper.convert(savedProduct, ProductResponse.class);
    }

    private void validateProductCode(String code) {
        if (productRepository.existsByCode(code)) {
            throw new DuplicateResourceException("Product",
                    "code",
                    code);
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productMapper.convertList(productRepository.findAll(), ProductResponse.class);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.convert(product, ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productDetails) {
        ProductResponse product = getProductById(id);

        BeanUtils.copyProperties(productDetails, product,
                "id", "createdAt", "code");

        Product updatedProduct = productRepository.save(productMapper.convert(product, Product.class));
        return productMapper.convert(updatedProduct, ProductResponse.class);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductResponse productDto = getProductById(id);
        productRepository.delete(productMapper.convert(productDto, Product.class));
    }
}
