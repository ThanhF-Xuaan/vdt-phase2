package com.demo.vdt.common.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtBlacklistValidatorTest {
    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private JwtBlacklistValidator validator;

    @Test
    void shouldReturnFailure_whenTokenIsInRedis() {
        Jwt mockJwt = Jwt.withTokenValue("mock-token")
                .header("alg", "none")
                .claim("jti", "token-bi-khoa")
                .build();

        Mockito.when(redisTemplate.hasKey("blacklist:token-bi-khoa"))
                .thenReturn(true);

        OAuth2TokenValidatorResult result = validator.validate(mockJwt);

        assertTrue(result.hasErrors());
    }
}