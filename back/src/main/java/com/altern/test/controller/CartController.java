package com.altern.test.controller;

import com.altern.test.entity.Cart;
import com.altern.test.entity.CartItem;
import com.altern.test.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    // Ajouter un produit au panier
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        Cart cart = cartService.addItemToCart(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    // Supprimer un item
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long itemId
    ) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.noContent().build();
    }

    // Modifier la quantité
    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        CartItem item = cartService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok(item);
    }

    // Afficher le panier
    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Cart cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }
}
