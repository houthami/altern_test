import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  {
    path: "paniers",
    loadChildren: () =>
      import("./panier/paniers.routes").then((m) => m.PANIERS_ROUTES)
  },
  {
    path: "contact",
    loadChildren: () =>
      import("./contact/contact.routes").then((m) => m.CONTACT_ROUT)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];
