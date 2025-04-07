package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DoanhThuDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.service.DoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doanh-thu")
public class DoanhThuAPI {

  @Autowired
    private DoanhThuService doanhThuService;

   @GetMapping("/calculate")
public ResponseEntity<DoanhThuDTO> getDoanhThuDetails(
        @RequestParam(required = false) Integer day,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year) {

    Map<String, Object> rawResult = doanhThuService.getDoanhThuDetails(day, month, year);

    // Chuyển đổi kết quả thành DTO
    DoanhThuDTO responseDTO = new DoanhThuDTO();
    responseDTO.setTotalRevenue((Double) rawResult.get("totalRevenue"));
    responseDTO.setTotalCount((Long) rawResult.get("totalCount"));
    responseDTO.setHoaDonList((List<HoaDonDTO>) rawResult.get("hoaDonList"));

    return ResponseEntity.ok(responseDTO);
}
}

