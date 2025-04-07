package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert (DecodedJWT jwt){
        var authorityList = getClaimOrEmptyList(jwt,"au").stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        return UserPrincipal.builder()
                .userId(Integer.valueOf(jwt.getSubject()))
                .userName(jwt.getClaim("username").asString())
                .authorities(authorityList)
                .build();
    }

    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim){
        if(jwt.getClaim(claim).isNull()) return List.of();
        return jwt.getClaim(claim).asList(String.class);
    }

}
