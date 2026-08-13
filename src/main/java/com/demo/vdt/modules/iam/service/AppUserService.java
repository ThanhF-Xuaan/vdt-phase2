package com.demo.vdt.modules.iam.service;

import com.demo.vdt.modules.iam.entity.AppUser;

public interface AppUserService {
    AppUser getOrCreate(String keycloakId,
                        String username,
                        String fullname);
}
