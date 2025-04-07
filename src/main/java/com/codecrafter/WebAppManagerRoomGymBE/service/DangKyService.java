package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;

import java.util.Date;
import java.util.List;

public interface DangKyService {
    DangKyE registerWithDiscountOrWithOutDiscount(int maThanhVien, int maGoiUuDai,Integer maLopHoc, Date ngayKichHoat, boolean trangThaiKichHoat);
//    DangKyE registerWithoutDiscount(int maThanhVien,int maGoiUuDai, Integer maLopHoc, Date ngayKichHoat, boolean trangThaiKichHoat);
    List<DangKyE> getDangKyByParams(Integer maDangKy, Integer maThanhVien, Integer maGoiUuDai, Date ngayDangKy, Date ngayKichHoat, Boolean trangThaiDangKy, Integer maLopHoc, Integer maHoaDon);
}
