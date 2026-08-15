package com.demo.vdt.modules.iam.service;

import com.demo.vdt.common.exception.AppException;
import com.demo.vdt.common.exception.ErrorCode;
import com.demo.vdt.common.utils.DateUtil;
import com.demo.vdt.modules.iam.dto.request.UserCreationRequest;
import com.demo.vdt.modules.iam.dto.request.UserUpdateRequest;
import com.demo.vdt.modules.iam.dto.response.UserInfoResponse;
import com.demo.vdt.modules.iam.entity.AppUser;
import com.demo.vdt.modules.iam.mapper.UserMapper;
import com.demo.vdt.modules.iam.repository.AppUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    AppUserRepository appUserRepository;
    Keycloak keycloak;
    UserMapper userMapper;

    @Override
    @Transactional
    public UserInfoResponse registerUser(UserCreationRequest userCreationRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userCreationRequest.getUsername());
        userRepresentation.setFirstName(userCreationRequest.getFirstName());
        userRepresentation.setLastName(userCreationRequest.getLastName());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userCreationRequest.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        try(Response response = keycloak.realm("demo-realm").users().create(userRepresentation)){
            if(response.getStatus() == 409){
                throw new AppException(ErrorCode.USER_EXISTED);
            }
            if(response.getStatus() != 201){
                log.error("Failed to create user in Keycloak. Status: {}", response.getStatus());
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }catch (WebApplicationException e){
            log.error("Error while creating Keycloak user", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        AppUser appUser = userMapper.toAppUser(userCreationRequest);
        try{
            appUserRepository.saveAndFlush(appUser);

            return userMapper.toUserInfoResponse(appUser);
        }catch (Exception e){
            log.error("Fail when saving DB MariaDB. Active Compensating Transaction delete user Keycloak...", e);

            rollbackKeycloakUser(userCreationRequest.getUsername());

            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public List<UserInfoResponse> getUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(userMapper::toUserInfoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoResponse getUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserInfoResponse(user);
    }

    @Override
    @Transactional
    public UserInfoResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<UserRepresentation> users = keycloak.realm("demo-realm").users().search(appUser.getUsername());
        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String keycloakId = users.get(0).getId();

        UserRepresentation userRepresentation = keycloak.realm("demo-realm").users().get(keycloakId).toRepresentation();
        userRepresentation.setFirstName(userUpdateRequest.getFirstName());
        userRepresentation.setLastName(userUpdateRequest.getLastName());

        if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isEmpty()) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userUpdateRequest.getPassword());
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        }

        keycloak.realm("demo-realm").users().get(keycloakId).update(userRepresentation);

        userMapper.updateAppUserFromRequest(userUpdateRequest, appUser);

        appUserRepository.save(appUser);

        return userMapper.toUserInfoResponse(appUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<UserRepresentation> users = keycloak.realm("demo-realm").users().search(appUser.getUsername());
        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String keycloakId = users.get(0).getId();

        keycloak.realm("demo-realm").users().get(keycloakId).remove();

        appUserRepository.delete(appUser);
    }

    private void rollbackKeycloakUser(String username){
        try{
            List<UserRepresentation> users = keycloak.realm("demo-realm").users().search(username);
            if(!users.isEmpty()){
                String keycloakId = users.get(0).getId();
                keycloak.realm("demo-realm").users().get(keycloakId).remove();
                log.info("Rollback successfully: user {} was deleted on Keycloak", username);
            }
        }catch (Exception ex){
            log.error("CRITICAL: Can't rollback user {} on Keycloak", username, ex);
        }
    }
}