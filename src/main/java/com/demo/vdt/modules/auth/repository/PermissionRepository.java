package com.demo.vdt.modules.auth.repository;

import com.demo.vdt.modules.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    @Query("SELECT COUNT(p) > 0 " +
            "FROM Permission p " +
            "JOIN RoleGroupPermission rgp " +
            "  ON rgp.permission.id = p.id " +
            "JOIN UserRoleGroup urg " +
            "  ON urg.roleGroup.id = rgp.roleGroup.id " +
            "WHERE urg.user.username = :username " +
            "  AND p.code = :permissionCode")
    boolean hasPermission(
            @Param("username") String username,
            @Param("permissionCode") String permissionCode
    );

    @Query("SELECT p.code " +
            "FROM Permission p " +
            "JOIN RoleGroupPermission rgp " +
            "ON rgp.permission.id = p.id " +
            "JOIN UserRoleGroup urg " +
            "ON urg.roleGroup.id = rgp.roleGroup.id " +
            "WHERE urg.user.username = :username")
    List<String> findPermissionCodesByUsername(@Param("username") String username);


    List<Permission> findAllByCodeIn(List<String> codes);


    @Query("SELECT p FROM Permission p " +
            "JOIN RoleGroupPermission rgp ON p.id = rgp.permission.id " +
            "WHERE rgp.roleGroup.id = :roleGroupId")
    List<Permission> findPermissionsByRoleGroupId(@Param("roleGroupId") Integer roleGroupId);

    boolean existsByCode(String code);
}
