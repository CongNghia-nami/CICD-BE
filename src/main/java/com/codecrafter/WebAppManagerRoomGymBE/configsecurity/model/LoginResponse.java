package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;
    private String tenNguoiDung;
     private String status;
    private String description;
    private String role;

}
