package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "gym_thanh_toan")
public class ThanhToanE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thanh_toan")
    private int maThanhToan;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", nullable = false) // Foreign key reference to HoaDonE

    private HoaDonE hoaDon;

    @Column(name = "ngay_thanh_toan")
    private Date ngayThanhToan;

    @Column(name = "so_tien_thanh_toan")
    private float soTienThanhToan;

    @Column(name = "phuong_thuc_thanh_toan")
    private String phuongThucThanhToan;

    @Column(name = "mo_ta_thanh_toan")
    private String moTaThanhToan;
}
