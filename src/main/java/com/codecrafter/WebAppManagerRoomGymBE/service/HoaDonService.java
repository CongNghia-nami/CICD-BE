package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    HoaDonE saveHoaDon(DangKyDTO registration);
    List<HoaDonE> findAll();
    Optional<HoaDonE> findHoaDonById(int maHoaDon);
    HoaDonE updateHoaDon(int maHoaDon, HoaDonDTO hoaDonDTO);
    List<HoaDonE> getHoaDonByIdAndOtherParam(Integer maHoaDon, Date ngayTaoHoaDon, Float soTienThanhToan);
}
