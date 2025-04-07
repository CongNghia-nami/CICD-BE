package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangKyRepo extends JpaRepository<DangKyE, Integer>, JpaSpecificationExecutor<DangKyE> {
     List<DangKyE> findByThanhVien_MaThanhVien(int maThanhVien);
}

