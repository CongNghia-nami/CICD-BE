
package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LopHocRepo extends JpaRepository<LopHocE, Integer> {

//    List<LopHocE> findByMaThanhVien(int maThanhVien);
    @Query("SELECT lh FROM LopHocE lh JOIN DangKyE dk ON lh.maLopHoc = dk.lopHoc.maLopHoc WHERE dk.thanhVien.maThanhVien = :maThanhVien")
    List<LopHocE> findByMaThanhVien(@Param("maThanhVien") int maThanhVien);

    Optional<LopHocE> findById(int maLopHoc);


}
