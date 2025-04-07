package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseTV {
      private final String accessToken;
    private String tenThanhVien;
    private int maThanhVien;
    private int maLichSuTapLuyen;
     private String status;
    private String description;

}
