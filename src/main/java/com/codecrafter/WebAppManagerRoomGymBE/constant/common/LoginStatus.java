package com.codecrafter.WebAppManagerRoomGymBE.constant.common;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum LoginStatus {

    NOT_EXIST("Tài khoản không tồn tại, vui lòng thử lại"),
    FAILED_PASSWORD("Mật khẩu không đúng, vui lòng thử lại"),
    SUCCEDD(null),
    ERROR("Phát sinh lỗi ngoài mong muốn");

    private String statusDescription;

    LoginStatus(String statusDescription){
        this.statusDescription = statusDescription;
    }

    public void setStatusDescription(String statusDescription){
        this.statusDescription = statusDescription;
    }
}
