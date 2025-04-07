
package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LopHocDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;

import java.util.List;

public interface LopHocService {

    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien);


    LopHocE addLopHoc(LopHocDTO lopHocDTO);
    LopHocE updateLopHoc(int maLopHoc, LopHocDTO lopHocDTO);
    void deleteLopHoc(int maLopHoc);
    List<LopHocM> getAllLopHoc();

}
