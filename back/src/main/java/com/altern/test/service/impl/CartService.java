package com.altern.test.service.impl;

import com.altern.test.dto.UserResponse;
import com.altern.test.entity.Cart;
import com.altern.test.entity.CartItem;
import com.altern.test.entity.Product;
import com.altern.test.entity.User;
import com.altern.test.repository.CartItemRepository;
import com.altern.test.repository.CartRepository;
import com.altern.test.repository.ProductRepository;
import com.altern.test.repository.UserRepository;
import com.altern.test.service.ICartService;
import com.altern.test.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final @Lazy IUserService userService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       @Lazy IUserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Cart addItemToCart(Long productId, int quantity) {
        UserResponse user = userService.getConnectedUser();
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity); // Mise à jour de la quantité
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long itemId) {
        Cart cart = cartRepository.findByUserId(userService.getConnectedUser().getId())
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item non trouvé"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
    }

    @Override
    public CartItem updateItemQuantity(Long itemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item non trouvé"));

        item.setQuantity(newQuantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart getCart() {
        return cartRepository.findByUserId(userService.getConnectedUser().getId())
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }

    @Override
    public Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
