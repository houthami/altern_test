import { Routes } from "@angular/router";
import { PanierListPageComponent } from "./features/panier-list/panier-list.component";

export const PANIERS_ROUTES: Routes = [
  {
    path: "list",
    component: PanierListPageComponent,
  },
  { path: "**", redirectTo: "list" },
];
