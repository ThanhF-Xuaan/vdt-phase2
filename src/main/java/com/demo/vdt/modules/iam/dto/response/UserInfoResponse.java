package com.demo.vdt.modules.iam.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    List<String> roleGroup;
}
