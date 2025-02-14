import { Injectable, signal, computed } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';

@Injectable({ providedIn: 'root' })
export class CartService {
  private _cartItems = signal<Product[]>([]);

  cartItems = this._cartItems.asReadonly();
  
  cartCount = computed(() => 
    this._cartItems().reduce((acc, item) => acc + (item.quantity || 1), 0)
  );

  addToCart(product: Product, quantity: number = 1) {
    const existingItem = this._cartItems().find(item => item.id === product.id);
    
    if (existingItem) {
      existingItem.quantity = (existingItem.quantity || 1) + quantity;
      this._cartItems.update(items => [...items]);
    } else {
      this._cartItems.update(items => [...items, { ...product, quantity }]);
    }
  }

  clearCart() {
    this._cartItems.set([]);
  }
}