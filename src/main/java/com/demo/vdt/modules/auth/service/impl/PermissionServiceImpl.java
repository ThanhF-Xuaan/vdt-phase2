package com.demo.vdt.modules.auth.service.impl;

import com.demo.vdt.common.constant.PermissionCode;
import com.demo.vdt.common.exception.AppException;
import com.demo.vdt.common.exception.ErrorCode;
import com.demo.vdt.modules.auth.dto.request.PermissionCreateRequest;
import com.demo.vdt.modules.auth.dto.request.PermissionUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.PermissionResponse;
import com.demo.vdt.modules.auth.entity.Permission;
import com.demo.vdt.modules.auth.mapper.PermissionMapper;
import com.demo.vdt.modules.auth.repository.PermissionRepository;
import com.demo.vdt.modules.auth.repository.RoleGroupPermissionRepository;
import com.demo.vdt.modules.auth.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    RoleGroupPermissionRepository roleGroupPermissionRepository;
    PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionResponse createPermission(PermissionCreateRequest request) {
        if (permissionRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        Permission permission = permissionMapper.toEntity(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toResponse(permission);
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(Integer id, PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updateEntity(permission, request);
        permissionRepository.save(permission);

        return permissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse getPermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        return permissionMapper.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        if (PermissionCode.CORE_PERMISSIONS.contains(permission.getCode())) {
            log.warn("Attempt to delete core permission blocked: {}", permission.getCode());
            throw new AppException(ErrorCode.CANNOT_DELETE_CORE_PERMISSION);
        }

        roleGroupPermissionRepository.deleteAllByPermissionId(id);

        permissionRepository.delete(permission);

        log.info("Successfully deleted custom permission: {}", permission.getCode());
    }
}