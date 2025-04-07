package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UuDaiDTO {

    private String moTaUuDai;

    private Date ngayBatDauUuDai;

    private Date ngayKetThucUuDai;

    private float giaTriUuDai;

    private boolean trangThaiUuDai;

//    private List<GoiUuDaiE> goiUuDais;
}
