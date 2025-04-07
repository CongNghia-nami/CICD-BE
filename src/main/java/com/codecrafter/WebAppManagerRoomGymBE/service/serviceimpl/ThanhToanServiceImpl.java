package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhToanRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhToanService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {

    @Autowired
    private ThanhToanRepo thanhToanRepository;

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public ThanhToanE createPayment(ThanhToanDTO thanhToanDTO) {
        ThanhToanE payment = new ThanhToanE();
        HoaDonE hoaDon = hoaDonRepository.findById(thanhToanDTO.getMaHoaDon())
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        payment.setHoaDon(hoaDon);
        payment.setNgayThanhToan(new Date());
        payment.setSoTienThanhToan(hoaDon.getSoTienThanhToan());
        payment.setPhuongThucThanhToan(thanhToanDTO.getPhuongThucThanhToan());
        payment.setMoTaThanhToan(thanhToanDTO.getMoTaThanhToan());

        return thanhToanRepository.save(payment);
    }

    @Override
    public Optional<ThanhToanE> findPaymentById(int maThanhToan) {
        return thanhToanRepository.findById(maThanhToan);
    }

    @Override
    public List<ThanhToanE> findAllPayments() {
        return thanhToanRepository.findAll();
    }

    @Override
    public ThanhToanE updatePayment(int maThanhToan, ThanhToanDTO thanhToanDTO) {
        ThanhToanE existingPayment = thanhToanRepository.findById(maThanhToan)
                .orElseThrow(() -> new RuntimeException("Thanh toán không tồn tại"));

//        existingPayment.setSoTienThanhToan(thanhToanDTO.getSoTienThanhToan());
        existingPayment.setPhuongThucThanhToan(thanhToanDTO.getPhuongThucThanhToan());
        existingPayment.setMoTaThanhToan(thanhToanDTO.getMoTaThanhToan());

        return thanhToanRepository.save(existingPayment);
    }

    @Override
    public List<ThanhToanE> getHoaDonByParams(Integer maHoaDon, Date ngayThanhToan, Float soTienThanhToan, String phuongThucThanhToan) {
        List<ThanhToanE> listThanhToan = thanhToanRepository.findAll((Root<ThanhToanE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (maHoaDon != null) {
                predicates.add(cb.equal(root.get("hoaDon").get("maHoaDon"), maHoaDon));
            }
            if (ngayThanhToan != null) {
                predicates.add(cb.equal(root.get("ngayThanhToan"), ngayThanhToan));
            }
            if (soTienThanhToan != null) {
                predicates.add(cb.equal(root.get("soTienThanhToan"), soTienThanhToan));
            }
            if (phuongThucThanhToan != null) {
                predicates.add(cb.equal(root.get("phuongThucThanhToan"), phuongThucThanhToan));
            }

            // Kết hợp các điều kiện với AND và trả về
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return listThanhToan;
    }

}
