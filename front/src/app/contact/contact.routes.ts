import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ContactFormComponent } from "./features/contact-form.component";

export const CONTACT_ROUT: Routes = [
  {
    path: "",
    component: ContactFormComponent,
  },
  { path: "**", redirectTo: "" },
];