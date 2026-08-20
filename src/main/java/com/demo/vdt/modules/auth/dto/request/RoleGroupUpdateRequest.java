package com.demo.vdt.modules.auth.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroupUpdateRequest {
    @NotBlank(message = "ROLE_GROUP_NAME_NOT_BLANK")
    String name;

    String description;

    @NotEmpty(message = "ROLE_GROUP_PERMISSIONS_NOT_EMPTY")
    List<String> permissionCodes;
}