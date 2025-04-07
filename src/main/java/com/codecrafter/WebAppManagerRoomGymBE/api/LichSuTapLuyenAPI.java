package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LichSuTapLuyenDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;
import com.codecrafter.WebAppManagerRoomGymBE.service.LichSuTapLuyenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api-public/lichsutapluyen")
public class LichSuTapLuyenAPI {

    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;

    @GetMapping("/thanhvien/{maThanhVien}")
    public ResponseObject<?> getLichSuTapLuyen(@PathVariable int maThanhVien) {
        var result = new ResponseObject<>();
        try {
            // Lấy thông tin UserPrincipal từ SecurityContext
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            boolean isAdminOrStaff = userPrincipal.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin") || authority.getAuthority().equals("ROLE_Staff"));

            // Nếu không phải Admin hoặc Staff và userId không khớp với maThanhVien, từ chối truy cập
            if (!isAdminOrStaff && userPrincipal.getUserId() != maThanhVien) {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Không có quyền truy cập lịch sử tập luyện của người dùng khác.");
                return result;
            }

            // Gọi service để lấy danh sách lịch sử tập luyện
            List<LichSuTapLuyenM> lichSuList = lichSuTapLuyenService.getLichSuTapLuyenByThanhVienId(maThanhVien);
            result.setData(lichSuList);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Lấy lịch sử tập luyện thành công cho thành viên có mã " + maThanhVien);

        } catch (Exception e) {
            log.info("Lỗi khi gọi API /api-public/lichsutapluyen/thanhvien/{maThanhVien} với mã thành viên " + maThanhVien, e);
            result.setStatus(BasicApiConstant.FAILED.getStatus());
            result.setMessages("Đã xảy ra lỗi khi lấy lịch sử tập luyện.");
        }
        return result;
    }

    @GetMapping("/thanhvien/")
    public ResponseObject<?> getAllLichSuTapLuyen() {
        var result = new ResponseObject<>();
        try {
            // Lấy thông tin UserPrincipal từ SecurityContext
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            boolean isAdminOrStaff = userPrincipal.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin") || authority.getAuthority().equals("ROLE_Staff"));

            // Nếu không phải Admin hoặc Staff và userId không khớp với maThanhVien, từ chối truy cập
            if (!isAdminOrStaff) {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Không có quyền truy cập lịch sử tập luyện của người dùng khác.");
                return result;
            }

            // Gọi service để lấy danh sách lịch sử tập luyện
            List<LichSuTapLuyenM> lichSuList = lichSuTapLuyenService.getAllLichSuTapLuyenMs();
            result.setData(lichSuList);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Lấy lịch sử tập luyện thành công cho thành viên");

        } catch (Exception e) {
            log.info("Lỗi khi gọi API /api-public/lichsutapluyen/thanhvien", e);
            result.setStatus(BasicApiConstant.FAILED.getStatus());
            result.setMessages("Đã xảy ra lỗi khi lấy lịch sử tập luyện.");
        }
        return result;
    }

    @PostMapping("/thanhvien/save")
    public ResponseObject<?> saveLichSuTapLuyen(@RequestBody LichSuTapLuyenDTO lichSuTapLuyenDTO) {
        var result = new ResponseObject<>();
        try {
            // Kiểm tra quyền
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isAdminOrStaff = userPrincipal.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_Admin") || authority.getAuthority().equals("ROLE_Staff"));

            if (!isAdminOrStaff && userPrincipal.getUserId() != lichSuTapLuyenDTO.getMaThanhVien()) {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Không có quyền lưu lịch sử tập luyện cho người dùng khác.");
                return result;
            }

            // Gọi service để lưu lịch sử tập luyện
            LichSuTapLuyenM savedLichSu = LichSuTapLuyenM.convertLichSuTapLuyenEToLichSuTapLuyenM(lichSuTapLuyenService.saveLichSuTapLuyen(lichSuTapLuyenDTO));

            result.setData(savedLichSu);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Lưu lịch sử tập luyện thành công.");

        } catch (Exception e) {
            log.info("Lỗi khi gọi API /api-public/lichsutapluyen/thanhvien/save", e);
            result.setStatus(BasicApiConstant.FAILED.getStatus());
            result.setMessages("Đã xảy ra lỗi khi lưu lịch sử tập luyện.");
        }
        return result;
    }



}
