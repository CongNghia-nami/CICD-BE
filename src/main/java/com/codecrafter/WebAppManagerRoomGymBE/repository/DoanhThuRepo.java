package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DoanhThuE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoanhThuRepo extends JpaRepository<DoanhThuE, Integer> {
      List<DoanhThuE> findByHoaDon(HoaDonE hoaDon); // Tìm doanh thu theo hóa đơn
}
