package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gym_dang_ky")
public class DangKyE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dang_ky")
    private int maDangKy;

    @ManyToOne
    @JoinColumn(name = "ma_thanh_vien")
    @JsonBackReference
    private ThanhVienE thanhVien;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_goi_uu_dai", nullable = true)
    @JsonBackReference
    private GoiUuDaiE goiUuDai;

    @Column(name = "ngay_dang_ky")
    private Date ngayDangKy;

    @Column(name = "ngay_kich_hoat")
    private Date ngayKichHoat;

    @Column(name = "trang_thai_dang_ky")
    private boolean trangThaiDangKy;

    @ManyToOne
    @JoinColumn(name = "ma_lop_hoc")
    @JsonBackReference
    private LopHocE lopHoc;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don"
    )
    @JsonBackReference
    private HoaDonE hoaDon;
}
