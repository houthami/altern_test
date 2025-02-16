package com.altern.test.service.impl;

import com.altern.test.dto.UserRequest;
import com.altern.test.dto.UserResponse;
import com.altern.test.entity.User;
import com.altern.test.mapper.GenericMapper;
import com.altern.test.numeration.Roles;
import com.altern.test.repository.UserRepository;
import com.altern.test.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = userMapper.convert(registrationDTO, User.class);
        user.setRole(Roles.CUSTOMER.name());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userMapper.convert(userRepository.save(user), UserResponse.class);
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


}
