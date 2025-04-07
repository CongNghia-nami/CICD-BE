package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class ThanhToanDTO {

    private int maHoaDon;
    private Date ngayThanhToan;
//    private float soTienThanhToan;
    private String phuongThucThanhToan;
    private String moTaThanhToan;
}
