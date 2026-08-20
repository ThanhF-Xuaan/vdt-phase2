package com.demo.vdt.common.security;

import com.demo.vdt.modules.auth.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    AuthorizationService authorizationService;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        if (username == null) {
            username = jwt.getSubject();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Object rolesObject = realmAccess.get("roles");

            if (rolesObject instanceof Collection<?>) {
                Collection<?> keycloakRoles = (Collection<?>) rolesObject;
                keycloakRoles.stream()
                        .map(Object::toString)
                        .map(role -> new SimpleGrantedAuthority("KEYCLOAK_" + role.toUpperCase()))
                        .forEach(authorities::add);
            }
        }

        List<String> roleGroups = authorizationService.getRoleGroups(username);
        List<String> permissions = authorizationService.getPermissions(username);

        List<String> safeRoleGroups = Optional.ofNullable(roleGroups).orElse(List.of());
        List<String> safePermissions = Optional.ofNullable(permissions).orElse(List.of());

        List<GrantedAuthority> dbAuthorities = Stream.concat(
                safeRoleGroups.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())),
                safePermissions.stream().map(SimpleGrantedAuthority::new)
        ).collect(Collectors.toList());

        authorities.addAll(dbAuthorities);

        return new JwtAuthenticationToken(jwt, authorities, username);
    }
}