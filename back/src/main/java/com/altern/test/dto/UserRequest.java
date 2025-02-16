package com.altern.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;
    private String firstname;
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
