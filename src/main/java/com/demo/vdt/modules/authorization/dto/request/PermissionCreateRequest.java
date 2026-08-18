package com.demo.vdt.modules.authorization.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionCreateRequest {

    @NotBlank(message = "PERMISSION_CODE_NOT_BLANK")
    @Size(max = 100, message = "Code length must not exceed 100 characters")
    String code;

    @NotBlank(message = "PERMISSION_NAME_NOT_BLANK")
    @Size(max = 150, message = "Name length must not exceed 150 characters")
    String name;

    @Size(max = 255, message = "Description length must not exceed 255 characters")
    String description;
}