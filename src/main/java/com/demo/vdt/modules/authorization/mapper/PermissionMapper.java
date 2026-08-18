package com.demo.vdt.modules.authorization.mapper;

import com.demo.vdt.modules.authorization.dto.request.PermissionCreateRequest;
import com.demo.vdt.modules.authorization.dto.request.PermissionUpdateRequest;
import com.demo.vdt.modules.authorization.dto.response.PermissionResponse;
import com.demo.vdt.modules.authorization.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    Permission toEntity(PermissionCreateRequest request);

    void updateEntity(@MappingTarget Permission entity, PermissionUpdateRequest request);

    PermissionResponse toResponse(Permission entity);
}