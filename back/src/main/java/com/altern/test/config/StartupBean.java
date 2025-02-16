package com.altern.test.config;

import com.altern.test.dto.UserRequest;
import com.altern.test.service.IUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupBean implements InitializingBean {

    @Autowired
    private IUserService userService;
    @Override
    public void afterPropertiesSet() {
        UserRequest userRequest = UserRequest.builder()
                .email("admin@admin.com")
                .username("admin")
                .password("pdmckld1523")
                .build();
        userService.initAdmin(userRequest);
    }
}