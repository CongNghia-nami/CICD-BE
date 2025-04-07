package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;

import java.util.List;
import java.util.Optional;

public interface NguoiDungService {
    Optional<NguoiDungE> login(NguoiDungDTO userDTO);

    Optional<NguoiDungE> findByUserName(String userName);// Thêm phương thức này

    Optional<NguoiDungE> getUserInfo(int id);

    void softDeleteUser(int id);

    List<NguoiDungE> getAllNguoiDung();
}
