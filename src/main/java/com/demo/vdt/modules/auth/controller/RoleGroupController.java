package com.demo.vdt.modules.auth.controller;

import com.demo.vdt.common.dto.ApiResponse;
import com.demo.vdt.modules.auth.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.auth.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.RoleGroupResponse;
import com.demo.vdt.modules.auth.service.RoleGroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role-groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleGroupController {

    RoleGroupService roleGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('ROLE_GROUP_CREATE')")
    public ApiResponse<RoleGroupResponse> createRoleGroup(@RequestBody @Valid RoleGroupCreateRequest request) {
        return ApiResponse.<RoleGroupResponse>builder()
                .result(roleGroupService.createRoleGroup(request))
                .build();
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_GROUP_UPDATE')")
    public ApiResponse<RoleGroupResponse> updateRoleGroup(
            @PathVariable Integer id,
            @RequestBody @Valid RoleGroupUpdateRequest request) {
        return ApiResponse.<RoleGroupResponse>builder()
                .result(roleGroupService.updateRoleGroup(id, request))
                .build();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_GROUP_READ')")
    public ApiResponse<RoleGroupResponse> getRoleGroup(@PathVariable Integer id) {
        return ApiResponse.<RoleGroupResponse>builder()
                .result(roleGroupService.getRoleGroup(id))
                .build();
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_GROUP_READ')")
    public ApiResponse<List<RoleGroupResponse>> getAllRoleGroups() {
        return ApiResponse.<List<RoleGroupResponse>>builder()
                .result(roleGroupService.getAllRoleGroups())
                .build();
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_GROUP_DELETE')")
    public ApiResponse<Void> deleteRoleGroup(@PathVariable Integer id) {
        roleGroupService.deleteRoleGroup(id);
        return ApiResponse.<Void>builder()
                .message("Role group deleted successfully")
                .build();
    }
}