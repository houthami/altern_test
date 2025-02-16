package com.altern.test.service;

import com.altern.test.dto.UserRequest;
import com.altern.test.dto.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest registrationDTO);
    UserResponse initAdmin(UserRequest registrationDTO);
}
