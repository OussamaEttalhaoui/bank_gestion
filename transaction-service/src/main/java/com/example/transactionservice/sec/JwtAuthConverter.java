package com.example.transactionservice.sec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities,jwt.getClaim("preferred_username"));
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String , Object> realmAccess;
        Collection<String> roles;
        if(jwt.getClaim("realm_access")==null){
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
    }

}

/*
{
        "exp": 1733586330,
        "iat": 1733586030,
        "jti": "7be2882c-4e71-42c4-8189-3ab2fa8ebb68",
        "iss": "http://localhost:8080/realms/bank-realm",
        "aud": "account",
        "sub": "0e218ebd-98b9-4386-b923-8fe048302202",
        "typ": "Bearer",
        "azp": "bank-client",
        "sid": "fc68f2c4-aa1a-4385-829d-ce2e6774528e",
        "acr": "1",
        "allowed-origins": [
        "/*"
        ],
        "realm_access": {
        "roles": [
        "default-roles-bank-realm",
        "offline_access",
        "ADMIN",
        "uma_authorization"
        ]
        },
        "resource_access": {
        "account": {
        "roles": [
        "manage-account",
        "manage-account-links",
        "view-profile"
        ]
        }
        },
        "scope": "email profile",
        "email_verified": false,
        "name": "oussama Ettalhaoui",
        "preferred_username": "user1",
        "given_name": "oussama",
        "family_name": "Ettalhaoui",
        "email": "user1@gmail.com"
        }*/
