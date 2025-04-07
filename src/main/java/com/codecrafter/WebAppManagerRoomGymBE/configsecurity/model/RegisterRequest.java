package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String tenNguoiDung;
    private String matKhauNguoiDung;
    private String role;

}
