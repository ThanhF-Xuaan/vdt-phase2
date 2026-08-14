package com.demo.vdt.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("master")                     // 1. Trỏ về realm master (nơi chứa tài khoản admin tối cao)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli")               // 2. Dùng client quản trị mặc định admin-cli có sẵn của Keycloak
                .username("admin")                   // 3. Tên tài khoản admin lúc bạn setup Docker Keycloak
                .password("admin")                   // 4. Mật khẩu tài khoản admin của bạn
                .build();
    }
}
