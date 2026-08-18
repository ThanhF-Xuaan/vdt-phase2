package com.demo.vdt.modules.authorization.service;

import com.demo.vdt.modules.authorization.dto.request.PermissionCreateRequest;
import com.demo.vdt.modules.authorization.dto.request.PermissionUpdateRequest;
import com.demo.vdt.modules.authorization.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreateRequest request);
    PermissionResponse updatePermission(Integer id, PermissionUpdateRequest request);
    PermissionResponse getPermission(Integer id);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(Integer id);
}