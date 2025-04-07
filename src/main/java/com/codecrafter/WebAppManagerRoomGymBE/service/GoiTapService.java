package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiTapDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiUuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;

import java.util.List;
import java.util.Optional;

public interface GoiTapService {
    List<GoiTapE> getAllGoiTap();
    Optional<GoiTapE> getGoiTapById(int maGoiTap);
    GoiTapE addGoiTap(GoiTapDTO goiTapDTO);
    GoiTapE updateGoiTap(int maGoiTap, GoiTapDTO goiTapDTO);
    void deleteGoiTap(int maGoiTap);
}
