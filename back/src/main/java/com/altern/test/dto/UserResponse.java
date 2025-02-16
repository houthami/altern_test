package com.altern.test.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;

    private String username;
    private String firstname;
    private String email;
    private String password;
}
