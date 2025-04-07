package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service

public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private DangKyRepo dangKyRepository;

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    @Transactional // Đảm bảo phương thức chạy trong một giao dịch
    public HoaDonE saveHoaDon(DangKyDTO registration) {
        // Kiểm tra đầu vào
        if (registration.getMaDangKy() == null) {
            throw new IllegalArgumentException("Mã đăng ký không được để trống");
        }

        // Tìm đối tượng đăng ký từ database
        DangKyE dangKy = dangKyRepository.findById(registration.getMaDangKy())
                .orElseThrow(() -> new RuntimeException("Đăng ký không tồn tại"));

        // Tạo mới hóa đơn
        HoaDonE invoice = new HoaDonE();
        invoice.setNgayTaoHoaDon(new Date()); // Gán ngày tạo hóa đơn
        invoice.setSoTienThanhToan(calculateTotalAmount(dangKy)); // Tính tổng số tiền cần thanh toán

        // Thiết lập mối quan hệ giữa hóa đơn và đăng ký
        dangKy.setHoaDon(invoice);
        invoice.setDangkys(Collections.singletonList(dangKy));


        // Lưu hóa đơn (cascade sẽ tự động lưu đăng ký nếu được cấu hình)
        HoaDonE savedInvoice = hoaDonRepository.save(invoice);

        return savedInvoice; // Trả về hóa đơn đã lưu
    }




    private float calculateTotalAmount(DangKyE dangKy) {

        float totalAmount = 0.0f;

        // Kiểm tra nếu có gói tập
        if (dangKy.getGoiUuDai() != null && dangKy.getGoiUuDai().getGoiTap() != null) {
            totalAmount += dangKy.getGoiUuDai().getGoiTap().getGiaGoiTap();
        }

        // Kiểm tra nếu có lớp học
        if (dangKy.getLopHoc() != null) {
            totalAmount += dangKy.getLopHoc().getGiaLopHoc();
        }

        return totalAmount;
    }


    @Override
    public List<HoaDonE> findAll() {
        List<HoaDonE> listHoaDon = hoaDonRepository.findAll();
        for (HoaDonE hoadon :listHoaDon)
        {
            hoadon.setTongHoaDon(listHoaDon.size());
        }
        return hoaDonRepository.findAll();
    }

    @Override
    public List<HoaDonE> getHoaDonByIdAndOtherParam(Integer maHoaDon, Date ngayTaoHoaDon, Float soTienThanhToan) {
        List<HoaDonE> listHoaDon = hoaDonRepository.findAll((Root<HoaDonE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (maHoaDon != null) {
                predicates.add(cb.equal(root.get("maHoaDon"), maHoaDon));
            }
            if (ngayTaoHoaDon != null) {
                predicates.add(cb.equal(root.get("ngayTaoHoaDon"), ngayTaoHoaDon));
            }
            if (soTienThanhToan != null) {
                predicates.add(cb.equal(root.get("soTienThanhToan"), soTienThanhToan));
            }

            // Kết hợp các điều kiện với AND và trả về
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        for (HoaDonE hoaDon : listHoaDon) {
            hoaDon.setTongHoaDon(listHoaDon.size());
        }

        return listHoaDon;
    }


    @Override
    public Optional<HoaDonE> findHoaDonById(int maHoaDon) {

        return hoaDonRepository.findById(maHoaDon);
    }

    @Override
    public HoaDonE updateHoaDon(int maHoaDon, HoaDonDTO hoaDonDTO) {

        return null;
    }
}
