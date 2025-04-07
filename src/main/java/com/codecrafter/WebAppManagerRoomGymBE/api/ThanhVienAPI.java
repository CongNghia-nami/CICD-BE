package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponseTV;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.LichSuTapLuyenService;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl.ThanhVienServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
public class ThanhVienAPI {
    @Autowired
    private ThanhVienService thanhVienService;

    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;

    @Autowired
    private final JwtIssuer jwtIssuer;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ThanhVienRepo thanhVienRepository;

    public ThanhVienAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseTV> login(@RequestBody ThanhVienDTO memberDTO) {
        // Kiểm tra tên thành viên
        if (memberDTO.getTenThanhVien() == null || memberDTO.getTenThanhVien().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên thành viên không được để trống")
                    .build());
        }
        // Kiểm tra mật khẩu
        if (memberDTO.getMatKhauNguoiDung() == null || memberDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống")
                    .build());
        }

        Optional<ThanhVienE> member = thanhVienService.login(memberDTO);
        if (member.isPresent()) {
            ThanhVienE thanhVien = member.get();

            if (!passwordEncoder.matches(memberDTO.getMatKhauNguoiDung(), thanhVien.getMatKhauNguoiDung())) {
                return ResponseEntity.status(401).body(LoginResponseTV.builder()
                        .accessToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }

            // Tạo yêu cầu để phát hành JWT
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) thanhVien.getMaThanhVien())
                    .username(thanhVien.getTenThanhVien())
                    .build();
            var token = jwtIssuer.issue(requestBuilder);

            // **Lấy maLichSuTapLuyen mới nhất của thành viên**
            int maLichSuTapLuyen = lichSuTapLuyenService.getNewestLichSuTapLuyenId(thanhVien.getMaThanhVien());

            // Tạo phản hồi LoginResponseTV với maLichSuTapLuyen và các thông tin khác
            LoginResponseTV response = LoginResponseTV.builder()
                    .accessToken(token)
                    .maLichSuTapLuyen(maLichSuTapLuyen) // Gán giá trị mới vào phản hồi
                    .maThanhVien(thanhVien.getMaThanhVien())
                    .tenThanhVien(thanhVien.getTenThanhVien())
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }

    }

    @GetMapping("/api-public/members/doGetALlMember")
    public Page<ThanhVienE> getMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maThanhVien") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return thanhVienService.getAllMembers(page, size, sortBy, ascending);
    }

    @DeleteMapping("/update/{maThanhVien}")
    public ResponseEntity<String> disableMember(@PathVariable int maThanhVien) {
        Optional<ThanhVienE> thanhVien = thanhVienService.disableMember(maThanhVien);
        if (thanhVien.isPresent()) {
            return ResponseEntity.ok("Thành viên đã bị khóa.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy thành viên với ID: " + maThanhVien);
        }
    }
    @PostMapping("/export")
    public ResponseEntity<String> exportCustomer(
            @RequestParam String name,
            @RequestParam int phone,
            @RequestParam String email
    ) {
        try {
            thanhVienService.exportToExcel(name, phone, email);
            return ResponseEntity.ok("Thêm khách hàng vào Excel thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

}
