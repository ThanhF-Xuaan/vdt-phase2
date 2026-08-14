package com.demo.vdt.modules.authorization.service;

import com.demo.vdt.common.exception.AppException;
import com.demo.vdt.common.exception.ErrorCode;
import com.demo.vdt.modules.authorization.repository.PermissionRepository;
import com.demo.vdt.modules.authorization.repository.RoleGroupRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationServiceImpl implements  AuthorizationService{
    PermissionRepository permissionRepository;
    RoleGroupRepository roleGroupRepository;

    @Override
    public void checkPermission(String username, String permissionCode, String functionName) {
        boolean hasPermission = permissionRepository.hasPermission(username, permissionCode);

        if(!hasPermission){
            throw new AppException(ErrorCode.FORBIDDEN,
                    String.format("user %s khong co quyen truy cap chuc nang",
                    username,
                    functionName));
        }
    }

    @Override
    public List<String> getPermissions(String username) {
        return permissionRepository.findPermissionCodesByUsername(username);
    }

    @Override
    public List<String> getRoleGroups(String username) {
        return roleGroupRepository.findRoleGroupCodesByUsername(username);
    }
}
