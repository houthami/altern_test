import {
  Component,
  inject,
  ViewChild
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { BadgeModule } from 'primeng/badge';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./services/cart.service";
import { CartDropdownComponent } from "./panier/ui/cart-dropdown/cart-dropdown.component";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, CartDropdownComponent, ToolbarModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  cartService = inject(CartService);
  cartCount = this.cartService.cartCount;
  @ViewChild('cartDropdown') cartDropdown!: CartDropdownComponent;
}
