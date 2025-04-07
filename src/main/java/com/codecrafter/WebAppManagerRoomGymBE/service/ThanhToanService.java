package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ThanhToanService {
    ThanhToanE createPayment(ThanhToanDTO thanhToanDTO);
    Optional<ThanhToanE> findPaymentById(int maThanhToan);
    List<ThanhToanE> findAllPayments();
    ThanhToanE updatePayment(int maThanhToan, ThanhToanDTO thanhToanDTO);
    public List<ThanhToanE> getHoaDonByParams(Integer maHoaDon, Date ngayThanhToan, Float soTienThanhToan, String phuongThucThanhToan) ;

}
