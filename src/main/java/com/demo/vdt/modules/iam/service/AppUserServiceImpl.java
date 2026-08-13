package com.demo.vdt.modules.iam.service;

import com.demo.vdt.modules.iam.entity.AppUser;
import com.demo.vdt.modules.iam.repository.AppUserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUserServiceImpl implements AppUserService{
    AppUserRepository appUserRepository;

    @Override
    @Transactional
    public AppUser getOrCreate(String keycloakId, String username, String fullname) {
        return appUserRepository
                .findById(keycloakId)
                .map(user -> updateUser(user, username, fullname))
                .orElseGet(() -> createUser(keycloakId, username, fullname));
    }

    private AppUser updateUser(AppUser user,
                               String username,
                               String fullname){
        user.setUsername(username);
        user.setFullName(fullname);

        return appUserRepository.save(user);
    }

    private AppUser createUser(String keycloakId,
                               String username,
                               String fullname){
        AppUser user = new AppUser();

        user.setKeycloakId(keycloakId);
        user.setUsername(username);
        user.setFullName(fullname);

        return appUserRepository.save(user);
    }
}
