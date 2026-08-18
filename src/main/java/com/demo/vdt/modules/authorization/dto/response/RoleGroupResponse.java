package com.demo.vdt.modules.authorization.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroupResponse {
    Integer id;
    String code;
    String name;
    String description;
    List<PermissionResponse> permissions;
}