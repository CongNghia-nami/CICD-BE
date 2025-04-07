package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "gym_doanh_thu")
public class DoanhThuE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_doanh_thu")
    private int maDoanhThu;

    @Column(name = "loai_thoi_gian_doanh_thu")
    private String loaiThoiGianDoanhThu;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", nullable = false) // Khóa ngoại tham chiếu đến HoaDonE
    @JsonBackReference
    private HoaDonE hoaDon;

    @Column(name = "so_tien_doanh_thu")
    private double soTienDoanhThu;  // Sử dụng double cho giá trị tiền

    @Column(name = "ngay_tao_doanh_thu")
    private Date ngayTaoDoanhThu;
}

