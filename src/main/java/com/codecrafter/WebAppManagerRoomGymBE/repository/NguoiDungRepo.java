package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepo extends JpaRepository<NguoiDungE, Integer> {
     Optional<NguoiDungE> findByTenNguoiDung(String tenNguoiDung);

}