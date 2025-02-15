import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: "app-contact-form",
  templateUrl: "./contact-form.component.html",
  styleUrls: ["./contact-form.component.scss"],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class ContactFormComponent implements OnInit {
  contactForm!: FormGroup;
  submitted = false;
  submitSuccess = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
      message: ["", [Validators.required, Validators.maxLength(300)]],
    });
  }

  get f() {
    return this.contactForm.controls;
  }

  get remainingChars(): number {
    return 300 - (this.contactForm.get("message")?.value?.length || 0);
  }

  onSubmit() {
    this.submitted = true;

    if (this.contactForm.invalid) {
      return;
    }

    // Simuler l'envoi du formulaire
    this.submitSuccess = true;
    this.contactForm.reset();
    this.submitted = false;

    // Masquer le message après 3 secondes
    setTimeout(() => {
      this.submitSuccess = false;
    }, 3000);
  }
}
