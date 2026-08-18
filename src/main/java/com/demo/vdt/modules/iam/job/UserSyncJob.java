package com.demo.vdt.modules.iam.job;

import com.demo.vdt.modules.iam.service.UserSyncService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSyncJob {
    UserSyncService userSyncService;

//    @Scheduled(fixedRate = 60000)
    public void runSyncJob(){
        log.info("Bắt đầu chạy Scheduled Job đồng bộ Keycloak...");
        userSyncService.syncUsersFromKeycloak();
    }
}
