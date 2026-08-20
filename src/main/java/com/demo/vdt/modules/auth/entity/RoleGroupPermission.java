package com.demo.vdt.modules.auth.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "role_group_permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleGroupPermission {
    @EmbeddedId
    RoleGroupPermissionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleGroupId")
    @JoinColumn(
            name = "role_group_id",
            nullable = false
    )
    RoleGroup roleGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(
            name = "permission_id",
            nullable = false
    )
    Permission permission;
}
