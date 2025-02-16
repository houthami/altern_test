package com.altern.test.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // Méthode utilitaire pour ajouter un produit au panier
    public void addItem(Product product, int quantity) {
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCart(this);
        items.add(item);
    }

    // Méthode utilitaire pour supprimer un produit du panier
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}