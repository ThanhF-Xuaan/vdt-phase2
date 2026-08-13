package com.demo.vdt.modules.authorization.repository;

import com.demo.vdt.modules.authorization.entity.RoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Integer> {
    Optional<RoleGroup> findByCode(String code);

    @Query("SELECT urg.roleGroup.code " +
            "FROM UserRoleGroup urg " +
            "WHERE urg.user.keycloakId = :keycloakId")
    List<String> findRoleGroupCodesByKeycloakId(@Param("keycloakId") String keycloakId);
}
