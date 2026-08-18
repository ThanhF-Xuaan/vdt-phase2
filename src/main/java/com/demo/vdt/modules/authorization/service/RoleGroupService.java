package com.demo.vdt.modules.authorization.service;

import com.demo.vdt.modules.authorization.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.authorization.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.authorization.dto.response.RoleGroupResponse;

import java.util.List;

public interface RoleGroupService {
    RoleGroupResponse createRoleGroup(RoleGroupCreateRequest request);
    RoleGroupResponse updateRoleGroup(Integer id, RoleGroupUpdateRequest request);
    RoleGroupResponse getRoleGroup(Integer id);
    List<RoleGroupResponse> getAllRoleGroups();
    void deleteRoleGroup(Integer id);
}