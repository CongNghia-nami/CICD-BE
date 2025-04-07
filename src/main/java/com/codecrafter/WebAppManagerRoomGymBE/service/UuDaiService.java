package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;

import java.util.Date;
import java.util.List;

public interface UuDaiService {

    UuDaiE createUuDai(UuDaiDTO uuDai);

    UuDaiE getUuDaiById(int id);

    List<UuDaiE> getAllUuDais();

    List<UuDaiE> getActiveUuDais();

    UuDaiE updateUuDai(int id, UuDaiDTO uuDaiDTO);

    UuDaiE deleteUuDai(int id);

    List<UuDaiE> getUuDaiByIdAndOtherParam(Integer maUuDai, Date ngayBatDau, Date ngayKetThuc, Boolean trangThaiUuDai);
}

