package com.demo.vdt.modules.auth.repository;

import com.demo.vdt.modules.auth.entity.RoleGroupPermission;
import com.demo.vdt.modules.auth.entity.RoleGroupPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleGroupPermissionRepository extends JpaRepository<RoleGroupPermission, RoleGroupPermissionId> {
    List<RoleGroupPermission> findAllByRoleGroupId(Integer roleGroupId);

    void deleteAllByRoleGroupId(Integer roleGroupId);

    void deleteAllByPermissionId(Integer permissionId);
}
