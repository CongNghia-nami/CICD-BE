package com.codecrafter.WebAppManagerRoomGymBE.data.model;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LopHocM {
    private int maLopHoc;
    private String tenLopHoc;
    private String moTaLopHoc;
    private float giaLopHoc;
    private Date lichHoc;
    private int soLuongThanhVienLopHoc;


    public static LopHocM convertLopHocEToLopHocM(LopHocE lopHocE) {
        return LopHocM.builder()
                .maLopHoc(lopHocE.getMaLopHoc())
                .tenLopHoc(lopHocE.getTenLopHoc())
                .moTaLopHoc(lopHocE.getMoTaLopHoc())
                .giaLopHoc(lopHocE.getGiaLopHoc())
                .lichHoc(lopHocE.getLichHoc())
                .soLuongThanhVienLopHoc(lopHocE.getSoLuongThanhVienLopHoc())
                .build();
    }

    public static List<LopHocM> convertListLopHocEToLopHocM(List<LopHocE> lopHocEList) {
        return lopHocEList.stream()
                .map(LopHocM::convertLopHocEToLopHocM)
                .collect(Collectors.toList());
    }
}
