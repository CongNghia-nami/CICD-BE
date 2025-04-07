package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuDTO {

      private double totalRevenue;
    private long totalCount;
    private List<HoaDonDTO> hoaDonList;

}
