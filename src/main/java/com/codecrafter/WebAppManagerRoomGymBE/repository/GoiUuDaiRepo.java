package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface GoiUuDaiRepo extends JpaRepository<GoiUuDaiE, Integer> {
    @Query("SELECT g FROM GoiUuDaiE g JOIN g.dangKy d WHERE d.thanhVien.maThanhVien = :maThanhVien")
    List<GoiUuDaiE> findByMaThanhVien(@Param("maThanhVien") int maThanhVien);

    Optional<GoiUuDaiE> findByMaGoiUuDai(int maGoiUuDai);

}

