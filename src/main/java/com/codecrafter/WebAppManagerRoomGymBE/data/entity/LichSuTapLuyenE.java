package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.Date;

@Data
@Entity
@Table(name = "gym_lich_su_tap_luyen")
public class LichSuTapLuyenE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maLichSuTapLuyen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ma_thanh_vien")
    @JsonBackReference
    private ThanhVienE thanhVien;

    private Date thoiGianTapLuyen;
    private String ghiChuTapLuyen;
}
