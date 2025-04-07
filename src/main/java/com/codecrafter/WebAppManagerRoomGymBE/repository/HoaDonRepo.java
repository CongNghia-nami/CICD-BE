package com.codecrafter.WebAppManagerRoomGymBE.repository;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDonE, Integer>, JpaSpecificationExecutor<HoaDonE> {
    HoaDonE findFirstByOrderByMaHoaDonDesc();
}

