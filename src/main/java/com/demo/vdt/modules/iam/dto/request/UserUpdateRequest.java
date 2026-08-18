package com.demo.vdt.modules.iam.dto.request;

import com.demo.vdt.modules.iam.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, message = "PASSWORD_INVALID_SIZE")
    String password;

    @NotBlank(message = "FIRST_NAME_NOT_BLANK")
    String firstName;

    @NotBlank(message = "LAST_NAME_NOT_BLANK")
    String lastName;

    @DobConstraint(min = 21, message = "INVALID_DOB")
    LocalDate dob;

    @NotEmpty(message = "USER_ROLE_GROUP_NOT_EMPTY")
    List<String> roleGroupCodes;
}