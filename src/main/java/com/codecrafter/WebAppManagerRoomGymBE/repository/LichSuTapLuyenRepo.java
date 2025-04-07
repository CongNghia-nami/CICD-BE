package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LichSuTapLuyenRepo extends JpaRepository<LichSuTapLuyenE, Integer> {
    List<LichSuTapLuyenE> findByThanhVien_MaThanhVien(int maThanhVien);

    Optional<LichSuTapLuyenE> findTopByThanhVien_MaThanhVienOrderByThoiGianTapLuyenDesc(int maThanhVien);

}
