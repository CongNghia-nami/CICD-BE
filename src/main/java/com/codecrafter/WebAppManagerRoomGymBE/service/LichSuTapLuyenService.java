package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LichSuTapLuyenDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;

import java.util.List;


public interface LichSuTapLuyenService {

    public List<LichSuTapLuyenM> getLichSuTapLuyenByThanhVienId(int maThanhVien);
    public List<LichSuTapLuyenM> getAllLichSuTapLuyenMs();

    int getNewestLichSuTapLuyenId(int maThanhVien);

    LichSuTapLuyenE saveLichSuTapLuyen(LichSuTapLuyenDTO lichSuTapLuyenDTO);


    }
