package com.altern.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(@NotBlank
                          String email,
                          @NotBlank @Size(min = 6) String password) {}
