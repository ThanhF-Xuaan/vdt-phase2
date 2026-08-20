package com.demo.vdt.modules.auth.repository;

import com.demo.vdt.modules.auth.entity.UserRoleGroup;
import com.demo.vdt.modules.auth.entity.UserRoleGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleGroupRepository extends JpaRepository<UserRoleGroup, UserRoleGroupId> {
    @Query("SELECT u FROM UserRoleGroup u WHERE u.id.userId = :userId")
    List<UserRoleGroup> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM UserRoleGroup u WHERE u.id.userId = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserRoleGroup u " +
            "WHERE u.id.userId = :userId AND u.id.roleGroupId = :roleGroupId")
    boolean existsByUserIdAndRoleGroupId(@Param("userId") Long userId, @Param("roleGroupId") Integer roleGroupId);
}
