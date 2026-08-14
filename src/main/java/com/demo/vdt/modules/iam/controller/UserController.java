package com.demo.vdt.modules.iam.controller;

import com.demo.vdt.common.dto.ApiResponse;
import com.demo.vdt.modules.iam.dto.request.UserCreationRequest;
import com.demo.vdt.modules.iam.dto.request.UserUpdateRequest;
import com.demo.vdt.modules.iam.dto.response.UserInfoResponse;
import com.demo.vdt.modules.iam.entity.AppUser;
import com.demo.vdt.modules.iam.service.AppUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    AppUserService appUserService;

    @GetMapping("/me")
    public ApiResponse<Object> me(Authentication authentication){
        return ApiResponse.builder()
                .result(authentication)
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserInfoResponse> register(@RequestBody UserCreationRequest userCreationRequest){

        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.registerUser(userCreationRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserInfoResponse>> getUsers(){
        return ApiResponse.<List<UserInfoResponse>>builder()
                .result(appUserService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserInfoResponse> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserInfoResponse> updateUser(@PathVariable("userId") Long userId,
                                                    @RequestBody UserUpdateRequest userUpdateRequest){
        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.updateUser(userId, userUpdateRequest))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") Long userId){
        appUserService.deleteUser(userId);

        return ApiResponse.<String>builder()
                .result("Delete user successfully!")
                .build();
    }
}
