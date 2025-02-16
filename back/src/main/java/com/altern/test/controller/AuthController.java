package com.altern.test.controller;

import com.altern.test.dto.AuthRequest;
import com.altern.test.dto.AuthResponse;
import com.altern.test.dto.UserRequest;
import com.altern.test.entity.User;
import com.altern.test.service.IUserService;
import com.altern.test.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        String jwt = userService.generateToken(authRequest);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/account")
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ResponseEntity<?> register(@Valid  @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}
