package com.demo.vdt.modules.auth.service.impl;

import com.demo.vdt.common.exception.AppException;
import com.demo.vdt.common.exception.ErrorCode;
import com.demo.vdt.modules.auth.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.auth.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.PermissionResponse;
import com.demo.vdt.modules.auth.dto.response.RoleGroupResponse;
import com.demo.vdt.modules.auth.entity.Permission;
import com.demo.vdt.modules.auth.entity.RoleGroup;
import com.demo.vdt.modules.auth.entity.RoleGroupPermission;
import com.demo.vdt.modules.auth.entity.RoleGroupPermissionId;
import com.demo.vdt.modules.auth.mapper.PermissionMapper;
import com.demo.vdt.modules.auth.mapper.RoleGroupMapper;
import com.demo.vdt.modules.auth.repository.PermissionRepository;
import com.demo.vdt.modules.auth.repository.RoleGroupPermissionRepository;
import com.demo.vdt.modules.auth.repository.RoleGroupRepository;
import com.demo.vdt.modules.auth.service.RoleGroupService;
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
public class RoleGroupServiceImpl implements RoleGroupService {

    RoleGroupRepository roleGroupRepository;
    PermissionRepository permissionRepository;
    RoleGroupPermissionRepository roleGroupPermissionRepository;

    RoleGroupMapper roleGroupMapper;
    PermissionMapper permissionMapper;

    @Override
    @Transactional
    public RoleGroupResponse createRoleGroup(RoleGroupCreateRequest request) {
        if (roleGroupRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.ROLE_GROUP_EXISTED);
        }

        RoleGroup roleGroup = roleGroupMapper.toEntity(request);
        roleGroup = roleGroupRepository.save(roleGroup);

        assignPermissionsToRoleGroup(roleGroup.getId(), request.getPermissionCodes());

        return buildRoleGroupResponse(roleGroup);
    }

    @Override
    @Transactional
    public RoleGroupResponse updateRoleGroup(Integer id, RoleGroupUpdateRequest request) {
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_GROUP_NOT_EXISTED));

        roleGroupMapper.updateEntity(roleGroup, request);
        roleGroupRepository.save(roleGroup);

        roleGroupPermissionRepository.deleteAllByRoleGroupId(id);
        assignPermissionsToRoleGroup(id, request.getPermissionCodes());

        return buildRoleGroupResponse(roleGroup);
    }

    @Override
    public RoleGroupResponse getRoleGroup(Integer id) {
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_GROUP_NOT_EXISTED));
        return buildRoleGroupResponse(roleGroup);
    }

    @Override
    public List<RoleGroupResponse> getAllRoleGroups() {
        return roleGroupRepository.findAll().stream()
                .map(this::buildRoleGroupResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRoleGroup(Integer id) {
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_GROUP_NOT_EXISTED));

        // Ràng buộc nghiệp vụ: Nếu Nhóm quyền này đang được gán cho User thì có cho xóa không?
        // (Nếu cần, check bảng user_role_groups trước ở đây và quăng lỗi "Nhóm quyền đang được sử dụng")

        roleGroupPermissionRepository.deleteAllByRoleGroupId(id);

        roleGroupRepository.delete(roleGroup);
    }


    // =========================================================
    // HÀM HỖ TRỢ (PRIVATE METHODS)
    // =========================================================

    private void assignPermissionsToRoleGroup(Integer roleGroupId,
                                              List<String> permissionCodes) {
        if (permissionCodes == null || permissionCodes.isEmpty()) return;

        List<Permission> validPermissions = permissionRepository.findAllByCodeIn(permissionCodes);

        RoleGroup proxyRoleGroup = roleGroupRepository.getOne(roleGroupId);

        List<RoleGroupPermission> mappings = validPermissions.stream().map(permission -> {
            RoleGroupPermission rgp = new RoleGroupPermission();
            rgp.setId(new RoleGroupPermissionId(roleGroupId, permission.getId()));

            rgp.setRoleGroup(proxyRoleGroup);
            rgp.setPermission(permission);

            return rgp;
        }).collect(Collectors.toList());

        roleGroupPermissionRepository.saveAll(mappings);
    }


    private RoleGroupResponse buildRoleGroupResponse(RoleGroup roleGroup) {
        RoleGroupResponse response = roleGroupMapper.toResponse(roleGroup);

        List<Permission> permissions = permissionRepository.findPermissionsByRoleGroupId(roleGroup.getId());

        List<PermissionResponse> permissionResponses = permissions.stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());

        response.setPermissions(permissionResponses);
        return response;
    }
}