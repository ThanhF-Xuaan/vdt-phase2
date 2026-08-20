package com.demo.vdt.modules.iam.service.impl;

import com.demo.vdt.modules.iam.entity.AppUser;
import com.demo.vdt.modules.iam.repository.AppUserRepository;
import com.demo.vdt.modules.iam.service.UserSyncService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSyncServiceImpl implements UserSyncService {
    final Keycloak keycloakAdminClient;
    final AppUserRepository appUserRepository;

    @Value("${keycloak.realm}")
    String targetRealm;

    @Override
    @Transactional
    public void syncUsersFromKeycloak() {
        log.info("Đồng bộ Users từ Keycloak");
        try{
            List<UserRepresentation> keyCloakUsers = keycloakAdminClient.realm(targetRealm).users().list();

            for(UserRepresentation kcUser : keyCloakUsers){
                AppUser appUser = appUserRepository.findByUsername(kcUser.getUsername())
                        .orElse(new AppUser());

                appUser.setUsername(kcUser.getUsername());
                appUser.setFirstName(kcUser.getFirstName());
                appUser.setLastName(kcUser.getLastName());

                appUserRepository.save(appUser);
            }

            log.info("Đồng bộ hoàn tất. Tổng số user: ", keyCloakUsers.size());
        }catch (Exception e){
            log.error("Lỗi khi đồng bộ users từ keycloak", e);
            throw new RuntimeException("Sync failed", e);
        }
    }
}
