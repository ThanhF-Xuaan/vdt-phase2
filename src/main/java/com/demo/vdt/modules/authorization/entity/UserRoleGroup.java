package com.demo.vdt.modules.authorization.entity;

import com.demo.vdt.modules.iam.entity.AppUser;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "user_role_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleGroup {
    @EmbeddedId
    UserRoleGroupId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleGroupId")
    @JoinColumn(
            name = "role_group_id",
            nullable = false
    )
    RoleGroup roleGroup;
}
