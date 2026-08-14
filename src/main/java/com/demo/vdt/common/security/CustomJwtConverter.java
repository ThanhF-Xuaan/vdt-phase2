package com.demo.vdt.common.security;

import com.demo.vdt.modules.authorization.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    AuthorizationService authorizationService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        if(username == null){
            username = jwt.getSubject();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if(realmAccess != null && realmAccess.containsKey("roles")){
            List<String> keycloakRoles = (List<String>) realmAccess.get("roles");

            List<SimpleGrantedAuthority> mappedRoles = keycloakRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLES_" + role.toUpperCase()))
                    .collect(Collectors.toList());
            authorities.addAll(mappedRoles);
        }

        List<String> roleGroups = authorizationService.getRoleGroups(username);
        List<String> permissions = authorizationService.getPermissions(username);

        List<GrantedAuthority> dbRoles = Stream.concat(
                        roleGroups.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)),
                        permissions.stream().map(permission -> new SimpleGrantedAuthority(permission))
                        )
                        .collect(Collectors.toList());

        authorities.addAll(dbRoles);

        return new JwtAuthenticationToken(jwt, authorities, username);
    }
}
