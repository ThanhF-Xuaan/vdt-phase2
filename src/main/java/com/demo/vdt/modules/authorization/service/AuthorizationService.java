package com.demo.vdt.modules.authorization.service;

import java.util.List;

public interface AuthorizationService {
    void checkPermission(String username,
                         String permissionCode,
                         String functionName);

    List<String> getPermissions(String keycloakId);

    List<String> getRoleGroups(String keycloakId);
}
