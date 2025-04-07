package com.codecrafter.WebAppManagerRoomGymBE.data.model;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class LichSuTapLuyenM {

    private int maLichSuTapLuyen;

    private Date thoiGianTapLuyen;
    private String ghiChuTapLuyen;

    public static LichSuTapLuyenM convertLichSuTapLuyenEToLichSuTapLuyenM(LichSuTapLuyenE lichSuTapLuyenE){
        return LichSuTapLuyenM.builder()
                .maLichSuTapLuyen(lichSuTapLuyenE.getMaLichSuTapLuyen())
                .thoiGianTapLuyen(lichSuTapLuyenE.getThoiGianTapLuyen())
                .ghiChuTapLuyen(lichSuTapLuyenE.getGhiChuTapLuyen())
                .build();
    }

    public static List<LichSuTapLuyenM> convertListLichSuTapLuyenEToListLichSuTapLuyenM(List<LichSuTapLuyenE> list){
        return list.stream()
                .map(LichSuTapLuyenM::convertLichSuTapLuyenEToLichSuTapLuyenM)
                .collect(Collectors.toList());
    }


}
