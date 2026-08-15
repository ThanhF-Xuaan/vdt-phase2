package com.demo.vdt.modules.iam.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank(message = "USERNAME_NOT_BLANK")
    @Size(min = 4, message = "USERNAME_INVALID_SIZE")
    String username;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @Size(min = 6, message = "PASSWORD_INVALID_SIZE")
    String password;

    @NotBlank(message = "FIRST_NAME_NOT_BLANK")
    String firstName;

    @NotBlank(message = "LAST_NAME_NOT_BLANK")
    String lastName;

    @NotBlank(message = "DOB_NOT_BLANK")
    String dob;
}