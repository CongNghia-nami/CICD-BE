package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity

@Table(name = "gym_thanh_vien")
public class ThanhVienE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thanh_vien")
    private int maThanhVien;

    @Column(name = "ten_thanh_vien")
    private String tenThanhVien;

    @Column(name = "email_thanh_vien")
    private String emailThanhVien;

    @Column(name = "mat_khau_nguoi_dung")
    private String matKhauNguoiDung;

    @Column(name = "so_dien_thoai_thanh_vien")
    private long soDienThoaiThanhVien;

    @Column(name = "ngay_sinh_thanh_vien")
    private Date ngaySinhThanhVien;

    @Column(name = "du_lieu_qr_dinh_danh", columnDefinition = "TEXT")
    private String duLieuQrDinhDanh;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ma_nguoi_dung")
    @JsonBackReference // Ngăn không tuần tự hóa NguoiDungE khi tuần tự hóa ThanhVienE
    private NguoiDungE nguoiDung;

//    @OneToMany(mappedBy = "thanhVien", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    @JsonBackReference
//    private List<DangKyE> dangKys = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "thanhVien", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LichSuTapLuyenE> lichSuTapLuyen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "thanhVien")
//    @JsonBackReference
    @JsonManagedReference
    private List<DangKyE> dangKy;
     @Column(name = "trang_thai_thanh_vien")
    private boolean trangThaiThanhVien;
}
