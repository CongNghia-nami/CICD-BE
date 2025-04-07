package com.codecrafter.WebAppManagerRoomGymBE.constant.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterStatus {

    ACCOUNT_EXISTED("Tài khoản đã tồn tại"),
    SUCCEED("Đã đăng ký tài khoản thành công"),
    FAILED("Đăng ký tài khoản thất bại"),
    ERROR("Phát sinh lỗi khi đăng ký tài khoản"),
    PENDING("Đang trong quá trình xác nhận"),
    VERIFY_SUCCEED("Xác thực thành công");

    private final String stateDescription;

}
