package com.demo.vdt.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakAuthorityMapper {
    public Collection<GrantedAuthority> map(OidcUser oidcUser){
        List<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realmAccess = oidcUser.getClaim("realm_access");

        if(realmAccess == null) return authorities;

        Object rolesObject = realmAccess.get("roles");

        if(!(rolesObject instanceof List)) return authorities;

        List<?> roles = (List<?>) rolesObject;

        for(Object role : roles){
            if(role instanceof String){
                authorities.add(new SimpleGrantedAuthority("ROLE" + role));
            }
        }

        return authorities;
    }
}
