package com.codecrafter.WebAppManagerRoomGymBE.constant.common;

import lombok.Getter;

@Getter
public enum BasicApiConstant {

    SUCCEED("Thành Công"),
    FAILED("Thất Bại"),
    ERROR("Phát Sinh Lỗi");

    private String status;

    BasicApiConstant(String status){
        this.status = status;
    }

}
    