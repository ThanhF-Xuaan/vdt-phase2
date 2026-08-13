package com.demo.vdt.modules.iam.controller;

import com.demo.vdt.common.dto.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @GetMapping("/me")
    public ApiResponse<Object> me(Authentication authentication){
        return ApiResponse.builder()
                .result(authentication)
                .build();
    }
}
