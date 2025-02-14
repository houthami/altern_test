// cart-page.component.ts
import { Component, inject } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService } from 'app/services/cart.service';

@Component({
  selector: 'app-cart-page',
  standalone: true,
  templateUrl: './panier-list.component.html',
  styleUrls: ['./panier-list.component.scss'],
  imports: [CurrencyPipe, CommonModule, RouterModule],
})
export class PanierListPageComponent {
  cartService = inject(CartService);
  cartItems = this.cartService.cartItems;
  cartTotal = this.cartService.cartTotal;
  currency = "MAD";

  public handleImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = 'assets/img/default-product.jpg';
    img.onerror = null; 
  }
}