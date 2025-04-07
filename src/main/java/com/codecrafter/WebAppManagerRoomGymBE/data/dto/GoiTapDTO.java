package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
public class GoiTapDTO {
    private int maGoiTap;
    private String tenGoiTap;
    private String moTaGoiTap;
    private float giaGoiTap;
    private Date thoiHanGoiTap;
    private boolean trangThaiGoiTap;
}
