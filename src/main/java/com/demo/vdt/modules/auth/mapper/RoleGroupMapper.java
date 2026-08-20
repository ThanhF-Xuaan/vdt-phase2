package com.demo.vdt.modules.auth.mapper;

import com.demo.vdt.modules.auth.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.auth.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.auth.dto.response.RoleGroupResponse;
import com.demo.vdt.modules.auth.entity.RoleGroup;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleGroupMapper {

    RoleGroup toEntity(RoleGroupCreateRequest request);

    void updateEntity(@MappingTarget RoleGroup entity, RoleGroupUpdateRequest request);

    RoleGroupResponse toResponse(RoleGroup entity);
}