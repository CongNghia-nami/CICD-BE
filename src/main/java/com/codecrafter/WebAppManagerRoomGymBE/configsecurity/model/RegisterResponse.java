package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String status;
    private String description;
    // Đảm bảo rằng constructor này là public

}
