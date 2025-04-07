package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "chi_tiet_hoa_don")
public class ChiTietHoaDonE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_hoa_don")
    private int maChiTietHoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", nullable = false)
    private HoaDonE hoaDon;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "don_gia")
    private float donGia;

    @Column(name = "tong_gia_tri")
    private float tongGiaTri;

    public void tinhTongGiatri() {
        this.tongGiaTri = this.soLuong * this.donGia;
    }
}
