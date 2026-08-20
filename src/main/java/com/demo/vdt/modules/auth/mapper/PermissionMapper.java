package com.demo.vdt.modules.auth.mapper;

import com.demo.vdt.modules.auth.dto.request.PermissionCreateRequest;
import com.demo.vdt.modules.auth.dto.request.PermissionUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.PermissionResponse;
import com.demo.vdt.modules.auth.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    Permission toEntity(PermissionCreateRequest request);

    void updateEntity(@MappingTarget Permission entity, PermissionUpdateRequest request);

    PermissionResponse toResponse(Permission entity);
}