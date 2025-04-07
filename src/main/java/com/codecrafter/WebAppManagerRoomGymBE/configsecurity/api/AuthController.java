package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginRequest;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        var requestBuilder = JwtIssuer.Request.builder()
                .userId(1L)
                .username(request.getUsername())
                .roles(List.of("USER"))
                .build();
        var token = jwtIssuer.issue(requestBuilder);
            return LoginResponse.builder()
                    .accessToken(token)
                    .build();
    }



    @GetMapping("/test")
    public String getSecured(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "if you see this, then you login with quang huy "+userPrincipal.getUsername();
    }

}
