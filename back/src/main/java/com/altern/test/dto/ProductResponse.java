package com.altern.test.dto;

import com.altern.test.numeration.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
