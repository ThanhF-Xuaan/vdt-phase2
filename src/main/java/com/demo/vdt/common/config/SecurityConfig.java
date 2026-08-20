package com.demo.vdt.common.config;

import com.demo.vdt.common.security.CustomJwtConverter;
import com.demo.vdt.common.security.JwtAuthenticationEntryPoint;
import com.demo.vdt.common.security.JwtBlacklistValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {
    final JwtBlacklistValidator jwtBlacklistValidator;
    final CustomJwtConverter customJwtConverter;
    final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    final String[] PUBLIC_ENDPOINTS = {"/",
            "/public/**",
            "/error",
            "/css/**",
            "/js/**",
            "/api/v1/users",
            "/api/v1/users/**",
    };

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        PUBLIC_ENDPOINTS
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .jwt()
                .jwtAuthenticationConverter(customJwtConverter);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators.createDefault();

        OAuth2TokenValidator<Jwt> delegatingValidator = new DelegatingOAuth2TokenValidator<>(defaultValidators, jwtBlacklistValidator);

        jwtDecoder.setJwtValidator(delegatingValidator);

        return jwtDecoder;
    }
}