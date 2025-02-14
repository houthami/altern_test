import { CommonModule } from "@angular/common";
import { Component, inject, model, viewChild } from "@angular/core";
import { CartService } from "app/services/cart.service";
import { ButtonModule } from "primeng/button";
import { OverlayPanel, OverlayPanelModule } from "primeng/overlaypanel";

@Component({
  selector: "app-cart-dropdown",
  standalone: true,
  templateUrl: "./cart-dropdown.component.html",
  styleUrls: ["./cart-dropdown.component.scss"],
  imports: [CommonModule, ButtonModule, OverlayPanelModule],
  /*template: `
    <p-overlayPanel #op [style]="{ width: '350px' }">
      <!-- cart content -->
    </p-overlayPanel>
  `,*/
})
export class CartDropdownComponent {
  isOpen = model(false);
  overlayPanelRef = viewChild<OverlayPanel>("op");

  
  cartService = inject(CartService);
  cartItems = this.cartService.cartItems;
  cartTotal = this.cartService.cartTotal;
  public currency  = "MAD";
  removeFromCart(productId: number) {
    this.cartService.removeFromCart(productId);
  }

  toggle(event: Event) {
    this.overlayPanelRef()?.toggle(event);
    this.isOpen.set(!this.isOpen());
  }
}
