package com.altern.test.service;

import com.altern.test.entity.Cart;
import com.altern.test.entity.CartItem;
import com.altern.test.entity.User;

public interface ICartService {
    Cart addItemToCart(Long productId, int quantity);

    void removeItemFromCart(Long itemId);

    CartItem updateItemQuantity(Long itemId, int newQuantity);

    Cart getCart();

    Cart createNewCart(User user);
}
