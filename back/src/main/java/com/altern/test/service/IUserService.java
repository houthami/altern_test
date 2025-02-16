package com.altern.test.service;

import com.altern.test.dto.AuthRequest;
import com.altern.test.dto.UserRequest;
import com.altern.test.dto.UserResponse;

public interface IUserService {

    String generateToken(AuthRequest authRequest);
    UserResponse createUser(UserRequest registrationDTO);
    UserResponse initAdmin(UserRequest registrationDTO);

    UserResponse getConnectedUser();
}
