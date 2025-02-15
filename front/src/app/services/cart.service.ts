import { computed, Injectable, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";

@Injectable({ providedIn: "root" })
export class CartService {
  private _cartItems = signal<Product[]>([]);

  cartItems = this._cartItems.asReadonly();

  cartCount = computed(() =>
    this._cartItems().reduce((acc, item) => acc + (item.quantityRequest || 1), 0)
  );

  addToCart(product: Product, quantity: number = 1) {
    const existingItem = this._cartItems().find(
      (item) => item.id === product.id
    );

    if (existingItem) {
      existingItem.quantityRequest = (existingItem.quantityRequest || 1) + quantity;
      this._cartItems.update((items) => [...items]);
    } else {
      this._cartItems.update((items) => [...items, { ...product, quantityRequest: quantity }]);
    }
  }

  cartTotal = computed(() =>
    this._cartItems().reduce(
      (acc, item) => acc + item.price * (item.quantityRequest || 1),
      0
    )
  );

  removeFromCart(productId: number) {
    this._cartItems.update((items) =>
      items.filter((item) => item.id !== productId)
    );
  }

  public decrement(item: Product) {
    if(!item || !item.quantityRequest) return
    if(item.quantityRequest === 1) return
    if (item.quantityRequest > 1) {
      item.quantityRequest--;
    }
  }

  public increment(item: Product) {
    if(!item || !item.quantityRequest) return
    if(item.quantityRequest === item.quantity) return
    item.quantityRequest++;
  }

  public updateQuantity(itemId: number, newQuantity: number) {
    const items = this.cartItems();
    const item = items.find((i) => i.id === itemId);
    if (item) {
      item.quantityRequest = Math.max(1, newQuantity);
      //this.cartItems.set(items);
    }
  }

  clearCart() {
    this._cartItems.set([]);
  }
}
