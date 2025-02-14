import { Component, ElementRef, OnInit, ViewChild, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { TagModule } from 'primeng/tag';
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { CommonModule } from "@angular/common";
import { CartService } from "app/services/cart.service";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [CommonModule, DataViewModule, CardModule, TagModule, ButtonModule, DialogModule, ProductFormComponent],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public isDescriptionExpanded = false;
  public currency = "MAD";
  private cartService = inject(CartService);
  @ViewChild('gridContainer') gridContainer!: ElementRef<HTMLElement>;

  public readonly editedProduct = signal<Product>(emptyProduct);

  ngOnInit() {
    this.productsService.get().subscribe();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  ngAfterViewInit() {
    const grid = this.gridContainer.nativeElement;
    
    grid.addEventListener('wheel', (e: WheelEvent) => {
      if (Math.abs(e.deltaY) > Math.abs(e.deltaX)) {
        e.preventDefault();
        grid.scrollLeft += e.deltaY * 2;
      }
    }, { passive: false });
  }

  public toggleDescription() {
    this.isDescriptionExpanded = !this.isDescriptionExpanded;
}

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public getInventoryStatus(quantity: number): string {
    if (quantity === 0) return 'OUTOFSTOCK';
    if (quantity < 10) return 'LOWSTOCK';
    return 'INSTOCK';
  }

  public getInventoryStatusClass(status: string): string {
    switch(status.toUpperCase()) {
      case 'LOWSTOCK':
        return 'low-stock';
      case 'OUTOFSTOCK':
        return 'out-of-stock';
      default:
        return '';
    }
  }

  public getSeverity(category: string) {
    switch (category.toLowerCase()) {
      case 'accessories':
        return 'info';
      case 'electronics':
        return 'success';
      case 'fashion':
        return 'warning';
      case 'home':
        return 'danger';
      default:
        return 'contrast'; // Changed from 'primary' to valid value
    }
  }

  public handleImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = 'assets/img/default-product.jpg';
    img.onerror = null; 
  }

  public addToCart(product: Product) {
    if (this.getInventoryStatus(product.quantity) !== 'OUTOFSTOCK') {
      this.cartService.addToCart(product);
      product.quantity -= 1;
      this.productsService.update(product).subscribe();
      this.animateCartIcon();
    }
  }
  
  private animateCartIcon() {
    const cartIcon = document.querySelector('.pi-shopping-cart');
    cartIcon?.classList.add('cart-bounce');
    setTimeout(() => cartIcon?.classList.remove('cart-bounce'), 500);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }
}
