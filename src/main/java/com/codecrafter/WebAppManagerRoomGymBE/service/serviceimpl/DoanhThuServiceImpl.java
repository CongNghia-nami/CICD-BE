package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DoanhThuE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DoanhThuRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.DoanhThuService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DoanhThuServiceImpl implements DoanhThuService {
    private final HoaDonRepo hoaDonRepository;
    private final DoanhThuRepo doanhThuRepository;

    @Override
    public Map<String, Object> getDoanhThuDetails(Integer day, Integer month, Integer year) {
        List<HoaDonE> hoaDons = hoaDonRepository.findAll((Root<HoaDonE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (year != null) {
                predicates.add(cb.equal(cb.function("DATE_PART", Double.class, cb.literal("YEAR"), root.get("ngayTaoHoaDon")), year));
            }
            if (month != null) {
                predicates.add(cb.equal(cb.function("DATE_PART", Double.class, cb.literal("MONTH"), root.get("ngayTaoHoaDon")), month));
            }
            if (day != null) {
                predicates.add(cb.equal(cb.function("DATE_PART", Double.class, cb.literal("DAY"), root.get("ngayTaoHoaDon")), day));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        // Tính tổng doanh thu và tổng số hóa đơn
        double totalRevenue = hoaDons.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();
        long totalCount = hoaDons.size();

        // Nếu không có hóa đơn nào, trả về kết quả mà không lưu vào database
        if (totalCount == 0) {
            Map<String, Object> result = new HashMap<>();
            result.put("totalRevenue", totalRevenue);
            result.put("totalCount", totalCount);
            result.put("hoaDonList", new ArrayList<>());
            return result;
        }

        // Tạo danh sách hóa đơn DTO
        List<HoaDonDTO> hoaDonDTOList = hoaDons.stream().map(hoaDon -> {
            HoaDonDTO dto = new HoaDonDTO();
            dto.setMaHoaDon(hoaDon.getMaHoaDon());
            dto.setNgayTaoHoaDon(hoaDon.getNgayTaoHoaDon());
            dto.setSoTienThanhToan(hoaDon.getSoTienThanhToan());
            return dto;
        }).collect(Collectors.toList());

        // Chỉ khi tìm thấy hóa đơn mới tạo đối tượng DoanhThu để lưu vào database
        DoanhThuE doanhThu = new DoanhThuE();
        doanhThu.setSoTienDoanhThu(totalRevenue);
        doanhThu.setNgayTaoDoanhThu(new Date());

        // Tạo loaiThoiGianDoanhThu dựa trên tham số đầu vào
        String loaiThoiGian = createLoaiThoiGian(day, month, year);

        // Kiểm tra loaiThoiGian và không cho phép lưu nếu nó rỗng
        if (loaiThoiGian.isEmpty()) {
            // Nếu không có thông tin xác định, không lưu vào database
            return new HashMap<>() {{
                put("totalRevenue", totalRevenue);
                put("totalCount", totalCount);
                put("hoaDonList", hoaDonDTOList);
            }};
        }

        doanhThu.setLoaiThoiGianDoanhThu(loaiThoiGian);
        doanhThu.setHoaDon(hoaDons.get(0)); // Lấy hóa đơn đầu tiên (hoặc có thể chọn hóa đơn nào đó khác)

        doanhThuRepository.save(doanhThu);

        // Tạo kết quả trả về
        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", totalRevenue);
        result.put("totalCount", totalCount);
        result.put("hoaDonList", hoaDonDTOList);

        return result;
    }

    private String createLoaiThoiGian(Integer day, Integer month, Integer year) {
        StringBuilder loaiThoiGian = new StringBuilder();
        boolean hasContent = false; // Đánh dấu nếu đã có nội dung

        if (day != null) {
            loaiThoiGian.append("Ngày ").append(day);
            hasContent = true; // Có nội dung cho ngày
        }
        if (month != null) {
            if (hasContent) {
                loaiThoiGian.append(" "); // Thêm khoảng trắng nếu có nội dung trước đó
            }
            loaiThoiGian.append("Tháng ").append(month);
            hasContent = true; // Có nội dung cho tháng
        }
        if (year != null) {
            if (hasContent) {
                loaiThoiGian.append(" "); // Thêm khoảng trắng nếu có nội dung trước đó
            }
            loaiThoiGian.append("Năm ").append(year);
        }

        return loaiThoiGian.toString().trim(); // Trả về chuỗi không có khoảng trắng thừa
    }


}

