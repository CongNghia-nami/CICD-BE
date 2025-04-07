package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HoaDonDTO {
     private int maHoaDon;
    private Date ngayTaoHoaDon;
    private float soTienThanhToan;

}
