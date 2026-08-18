package com.demo.vdt.modules.authorization.mapper;

import com.demo.vdt.modules.authorization.dto.request.RoleGroupCreateRequest;
import com.demo.vdt.modules.authorization.dto.request.RoleGroupUpdateRequest;
import com.demo.vdt.modules.authorization.dto.response.RoleGroupResponse;
import com.demo.vdt.modules.authorization.entity.RoleGroup;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleGroupMapper {

    RoleGroup toEntity(RoleGroupCreateRequest request);

    void updateEntity(@MappingTarget RoleGroup entity, RoleGroupUpdateRequest request);

    RoleGroupResponse toResponse(RoleGroup entity);
}