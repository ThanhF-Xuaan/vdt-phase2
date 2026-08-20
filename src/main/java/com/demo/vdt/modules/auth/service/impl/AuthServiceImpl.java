package com.demo.vdt.modules.auth.service.impl;

import com.demo.vdt.modules.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthServiceImpl implements AuthService {
    final StringRedisTemplate redisTemplate;
    final RestTemplate restTemplate;

    @Value("${keycloak.admin.client-id}")
    String clientId;

    @Value("${keycloak.client-secret:}")
    String clientSecret;

    @Value("${keycloak.admin.revoke-uri}")
    String revokeUri;

    @Override
    public void logout(String refreshToken) {
        log.info("Bat dau logout: ");

        blacklistCurrentAccessToken();

        revokeTokenAtKeycloak(refreshToken);
    }

    private void blacklistCurrentAccessToken(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof Jwt){
            Jwt jwt = (Jwt) authentication.getPrincipal();

            String jti = jwt.getId();
            Instant expiresAt = jwt.getExpiresAt();

            if(expiresAt != null){
                Duration ttl = Duration.between(Instant.now(), expiresAt);
                if(!ttl.isNegative()){
                    redisTemplate.opsForValue().set("blacklist:" + jti, "true", ttl);
                    log.info("Da dua Access Token (jti: {}) vao Redis voi TTL: {} giay", jti, ttl.getSeconds());
                }
            }
        }
    }

    private void revokeTokenAtKeycloak(String refreshToken){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", clientId);

            if (clientSecret != null && !clientSecret.isEmpty()){
                body.add("client_secret", clientSecret);
            }

            body.add("token", refreshToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(revokeUri, request, String.class);
            log.info("Revoke token");
        } catch (Exception e) {
            log.error("Loi khi goi API Revoke cua Keycloak", e);
        }
    }
}
