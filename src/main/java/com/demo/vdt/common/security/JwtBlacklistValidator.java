package com.demo.vdt.common.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtBlacklistValidator implements OAuth2TokenValidator<Jwt> {
    StringRedisTemplate redisTemplate;


    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String jti = jwt.getId();
        Boolean isBlacklisted = redisTemplate.hasKey("blacklist:" + jti);

        if(isBlacklisted){
            log.warn("CẢNH BÁO: Kẻ gian đang cố sử dụng Token đã bị thu hồi! JTI: {}", jti);

            OAuth2Error error = new OAuth2Error("invalid_token", "The token has been revoked", null);
            return OAuth2TokenValidatorResult.failure(error);
        }

        return OAuth2TokenValidatorResult.success();
    }
}

