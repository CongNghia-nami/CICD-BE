package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
public class LoginRequest {

    private String username;
    private String password;
}
