package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
public class ThanhVienDTO {
    private int maThanhVien;
    private String tenThanhVien;
    private String matKhauNguoiDung;
    private String emailThanhVien;
    private long soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private String duLieuQrDinhDanh;
    private boolean trangThaiThanhVien;

//    private int maGoiTap; // Thêm trường này
    private int maDangKy;

}
