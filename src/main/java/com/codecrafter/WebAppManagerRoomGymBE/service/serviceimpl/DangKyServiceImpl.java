package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.*;

import com.codecrafter.WebAppManagerRoomGymBE.repository.*;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import com.codecrafter.WebAppManagerRoomGymBE.utils.EmailService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DangKyServiceImpl implements DangKyService {

    private final DangKyRepo dangKyRepository;
    private final ThanhVienRepo thanhVienRepository;
    private final GoiUuDaiRepo goiUuDaiRepository;
    private final LopHocRepo lopHocRepo;
//    private final SendMailService sendMailService;
    private final GoiTapRepo goiTapRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public DangKyE registerWithDiscountOrWithOutDiscount(int maThanhVien, int maGoiUuDai, Integer maLopHoc, Date ngayKichHoat, boolean trangThaiDangKy) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Validate discount
        GoiUuDaiE goiUuDai = goiUuDaiRepository.findByMaGoiUuDai(maGoiUuDai)
                .orElseThrow(() -> new RuntimeException("Invalid or expired discount code"));

        DangKyE dangKy = new DangKyE();
        dangKy.setGoiUuDai(goiUuDai);

        GoiTapE goiTap = goiUuDai.getGoiTap();
        if (goiTap == null) {
            throw new RuntimeException("No associated package found for this discount");
        }
        if (maLopHoc != null && maLopHoc != 0) {
            LopHocE lopHoc = lopHocRepo.findById(maLopHoc)
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            dangKy.setLopHoc(lopHoc);
        } else {
            dangKy.setLopHoc(null);
        }

        dangKy.setThanhVien(thanhVien);
        dangKy.setNgayDangKy(new Date());
        dangKy.setNgayKichHoat(ngayKichHoat);
        dangKy.setTrangThaiDangKy(trangThaiDangKy);


        DangKyE savedDangKy = dangKyRepository.save(dangKy);

        // Thiết lập các thuộc tính cho DTO
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setTenThanhVien(thanhVien.getTenThanhVien());
        thanhVienDTO.setEmailThanhVien(thanhVien.getEmailThanhVien());
        thanhVienDTO.setSoDienThoaiThanhVien(thanhVien.getSoDienThoaiThanhVien());
        thanhVienDTO.setNgaySinhThanhVien(thanhVien.getNgaySinhThanhVien());
        thanhVienDTO.setDuLieuQrDinhDanh(thanhVien.getDuLieuQrDinhDanh());
        thanhVienDTO.setMaDangKy(savedDangKy.getMaDangKy());

        // Lấy mật khẩu gốc từ thanhVien
        String originalPassword = thanhVien.getMatKhauNguoiDung();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        thanhVien.setMatKhauNguoiDung(encodedPassword);
        emailService.emailTemplate(
                goiTap.getTenGoiTap(),
                goiTap.getMoTaGoiTap(),
                goiTap.getGiaGoiTap(),
                thanhVienDTO.getTenThanhVien(),
                thanhVienDTO.getEmailThanhVien(),
                originalPassword,
                String.valueOf(thanhVienDTO.getSoDienThoaiThanhVien()),
                thanhVienDTO.getNgaySinhThanhVien(),
                thanhVienDTO.getDuLieuQrDinhDanh()
        );

        return savedDangKy;
    }







    @Override
    public List<DangKyE> getDangKyByParams(Integer maDangKy, Integer maThanhVien, Integer maGoiUuDai, Date ngayDangKy, Date ngayKichHoat, Boolean trangThaiDangKy, Integer maLopHoc, Integer maHoaDon) {
        return dangKyRepository.findAll((Root<DangKyE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Điều kiện cho từng trường
            if (maDangKy != null) {
                predicates.add(cb.equal(root.get("maDangKy"), maDangKy));
            }
            if (maThanhVien != null) {
                predicates.add(cb.equal(root.get("thanhVien").get("maThanhVien"), maThanhVien));
            }
            if (maGoiUuDai != null) {
                predicates.add(cb.equal(root.get("goiUuDai").get("maGoiUuDai"), maGoiUuDai));
            }
            if (ngayDangKy != null) {
                predicates.add(cb.equal(root.get("ngayDangKy"), ngayDangKy));
            }
            if (ngayKichHoat != null) {
                predicates.add(cb.equal(root.get("ngayKichHoat"), ngayKichHoat));
            }
            if (trangThaiDangKy != null) {
                predicates.add(cb.equal(root.get("trangThaiDangKy"), trangThaiDangKy));
            }
            if (maLopHoc != null) {
                predicates.add(cb.equal(root.get("lopHoc").get("maLopHoc"), maLopHoc));
            }
            if (maHoaDon != null) {
                predicates.add(cb.equal(root.get("hoaDon").get("maHoaDon"), maHoaDon));
            }

            // Kết hợp các điều kiện
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}

