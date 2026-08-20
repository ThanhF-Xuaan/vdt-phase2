package com.demo.vdt.modules.auth.controller;

import com.demo.vdt.common.dto.ApiResponse;
import com.demo.vdt.modules.auth.service.AuthService;
import com.demo.vdt.modules.auth.dto.request.LogoutRequest;
import com.demo.vdt.modules.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/logout")
    public ApiResponse<String> logout(@Valid @RequestBody LogoutRequest request) {
        // Gọi xuống Service để thực hiện logic (Blacklist Access Token + Revoke Refresh Token)
        authService.logout(request.getRefreshToken());

        return ApiResponse.<String>builder()
                .message("Dang xuat thanh cong")
                .build();
    }
}