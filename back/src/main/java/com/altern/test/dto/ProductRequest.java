package com.altern.test.dto;

import com.altern.test.numeration.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private Long id;

    @NotBlank(message = "Product code is required")
    private String code;

    @NotBlank(message = "Product name is required")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    private String image;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Quantity must be positive")
    private int quantity;
    private String internalReference;
    private Long shellId;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
