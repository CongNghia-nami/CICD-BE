package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="gym_lop_hoc")
public class LopHocE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lop_hoc")
    private int maLopHoc;

    @Column(name = "ten_lop_hoc", length = 50)
    private String tenLopHoc;

    @Column(name = "mo_ta_lop_hoc", length = 200)
    private String moTaLopHoc;

    @Column(name = "gia_lop_hoc")
    private float giaLopHoc;

    @Column(name = "lich_hoc")
    @Temporal(TemporalType.DATE)
    private Date lichHoc;

    @Column(name = "so_luong_thanh_vien_lop_hoc")
    private int soLuongThanhVienLopHoc;

    @Column(name = "trang_thai_lop_hoc", nullable = false)
    private boolean trangThaiLopHoc;
}
