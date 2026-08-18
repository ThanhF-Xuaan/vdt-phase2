package com.demo.vdt.modules.iam.mapper;

import com.demo.vdt.common.utils.DateUtil;
import com.demo.vdt.modules.iam.dto.request.UserCreationRequest;
import com.demo.vdt.modules.iam.dto.request.UserUpdateRequest;
import com.demo.vdt.modules.iam.dto.response.UserInfoResponse;
import com.demo.vdt.modules.iam.entity.AppUser;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        imports = {DateUtil.class}
)
public interface UserMapper {
    @Mapping(target = "userId", ignore = true)
    AppUser toAppUser(UserCreationRequest request);

    UserInfoResponse toUserInfoResponse(AppUser appUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "username", ignore = true)
    void updateAppUserFromRequest(UserUpdateRequest request, @MappingTarget AppUser appUser);
}
