package com.demo.vdt.modules.iam.service;

import com.demo.vdt.modules.iam.dto.request.UserCreationRequest;
import com.demo.vdt.modules.iam.dto.request.UserUpdateRequest;
import com.demo.vdt.modules.iam.dto.response.UserInfoResponse;

import java.util.List;

public interface AppUserService {
    UserInfoResponse registerUser(UserCreationRequest userCreationRequest);

    List<UserInfoResponse> getUsers();

    UserInfoResponse getUser(Long userId);

    UserInfoResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long userId);
}
