package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LopHocDTO {
    private int maLopHoc;
    private String tenLopHoc;
    private String moTaLopHoc;
    private float giaLopHoc;
    private Date lichHoc;
    private int soLuongThanhVienLopHoc;
    private boolean trangThaiLopHoc;
}
