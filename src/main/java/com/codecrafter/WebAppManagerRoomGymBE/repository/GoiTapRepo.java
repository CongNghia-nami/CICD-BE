package com.codecrafter.WebAppManagerRoomGymBE.repository;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoiTapRepo extends JpaRepository<GoiTapE, Integer> {
     Optional<GoiTapE> findById(int id);
}
