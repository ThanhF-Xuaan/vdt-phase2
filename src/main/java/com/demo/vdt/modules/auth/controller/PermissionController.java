package com.demo.vdt.modules.auth.controller;

import com.demo.vdt.common.dto.ApiResponse;
import com.demo.vdt.modules.auth.dto.request.PermissionCreateRequest;
import com.demo.vdt.modules.auth.dto.request.PermissionUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.PermissionResponse;
import com.demo.vdt.modules.auth.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionCreateRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ApiResponse<PermissionResponse> updatePermission(
            @PathVariable Integer id,
            @RequestBody @Valid PermissionUpdateRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(id, request))
                .build();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ApiResponse<PermissionResponse> getPermission(@PathVariable Integer id) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getPermission(id))
                .build();
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ApiResponse<Void> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder()
                .message("Permission deleted successfully")
                .build();
    }
}