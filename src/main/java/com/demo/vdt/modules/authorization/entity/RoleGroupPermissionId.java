package com.demo.vdt.modules.authorization.entity;

import liquibase.pro.packaged.A;
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
public class RoleGroupPermissionId implements Serializable {
    Integer roleGroupId;

    Integer permissionId;
}
