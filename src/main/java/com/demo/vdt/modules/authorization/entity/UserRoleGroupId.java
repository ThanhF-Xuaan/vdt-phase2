package com.demo.vdt.modules.authorization.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleGroupId implements Serializable {

    private String userId;

    private Long roleGroupId;
}
