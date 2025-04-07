package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class HuanLuyenVienE {
    private int maHuanLuyenVien;
    private String tenHuanLuyenVien;
    private String moTaLopHoc;
    private String emailHuanLuyenVien;
    private String soDienThoaiHuanLuyenVien;
    private String chuyenMonHuanLuyenVien;
    private Date lichLamViecHuanLuyenVien;
    private int maLopHoc;
    private String duLieuQrDinhDanh;
    @Column(name = "trang_thai_huan_luyen_vien", nullable = false)
    private boolean trangThaiHuanLuyenVien;
}
