package com.altern.test.service.impl;

import com.altern.test.dto.AuthRequest;
import com.altern.test.dto.UserRequest;
import com.altern.test.dto.UserResponse;
import com.altern.test.entity.User;
import com.altern.test.exception.AuthException;
import com.altern.test.mapper.GenericMapper;
import com.altern.test.numeration.Roles;
import com.altern.test.repository.UserRepository;
import com.altern.test.service.ICartService;
import com.altern.test.service.IUserService;
import com.altern.test.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final @Lazy ICartService cartService;
    private final GenericMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Lazy ICartService cartService,
                       GenericMapper userMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String generateToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
        } catch (BadCredentialsException e) {
            throw new AuthException("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email());
        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = userMapper.convert(registrationDTO, User.class);
        user.setRole(Roles.CUSTOMER.name());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        User savedUser = userRepository.save(user);
        cartService.createNewCart(savedUser);
        return userMapper.convert(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse initAdmin(UserRequest registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = userMapper.convert(registrationDTO, User.class);
        user.setRole(Roles.ADMIN.name());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userMapper.convert(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse getConnectedUser() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return userMapper.convert(userRepository.findById(userId).orElseThrow(), UserResponse.class);
    }


}
