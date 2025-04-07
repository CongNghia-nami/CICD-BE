package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Getter
@Setter

@Table(name = "gym_nguoi_dung")
public class NguoiDungE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maNguoiDung;
    private String tenNguoiDung;
    private String matKhauNguoiDung;
    private boolean gioiTinhNguoiDung;
    private String soDienThoaiNguoiDung;
    private String moTaNguoiDung;
    private String anhNguoiDung;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_vai_tro")
    @JsonBackReference
    private VaiTroE vaiTro;
    private boolean hoatDongNguoiDung;
    private String duLieuQrDinhDanh;
    @Column(name = "trang_thai_nguoi_dung", nullable = false)
    private boolean trangThaiNguoiDung;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ThanhVienE> thanhVien;
}
