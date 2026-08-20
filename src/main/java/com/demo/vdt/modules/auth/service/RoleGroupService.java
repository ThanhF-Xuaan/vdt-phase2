package com.demo.vdt.modules.auth.service;

import com.demo.vdt.modules.auth.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.auth.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.RoleGroupResponse;

import java.util.List;

public interface RoleGroupService {
    RoleGroupResponse createRoleGroup(RoleGroupCreateRequest request);
    RoleGroupResponse updateRoleGroup(Integer id, RoleGroupUpdateRequest request);
    RoleGroupResponse getRoleGroup(Integer id);
    List<RoleGroupResponse> getAllRoleGroups();
    void deleteRoleGroup(Integer id);
}