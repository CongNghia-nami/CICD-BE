package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiTapDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api-public/goi-tap")
@RequiredArgsConstructor
public class GoiTapAPI {

    @Autowired
    private GoiTapService goiTapService;

    // Lấy tất cả các gói tập
    @GetMapping("/getAllGoiTap")
    public ResponseObject<?> getAllGoiTap() {
        var result = new ResponseObject<>();
        try {
            List<GoiTapE> goiTapList = goiTapService.getAllGoiTap();
            result.setData(goiTapList);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Lấy danh sách gói tập thành công.");
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả gói tập", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi lấy tất cả gói tập.");
        }
        return result;
    }

    // Lấy thông tin gói tập theo ID
    @GetMapping("/getGoiTapById/{maGoiTap}")
    public ResponseObject<?> getGoiTapById(@PathVariable int maGoiTap) {
        var result = new ResponseObject<>();
        try {
            Optional<GoiTapE> goiTap = goiTapService.getGoiTapById(maGoiTap);
            if (goiTap.isPresent()) {
                result.setData(goiTap.get());
                result.setStatus(BasicApiConstant.SUCCEED.getStatus());
                result.setMessages("Lấy gói tập thành công.");
            } else {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Không tìm thấy gói tập.");
            }
        } catch (Exception e) {
            log.error("Lỗi khi lấy gói tập theo ID", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi lấy gói tập theo ID.");
        }
        return result;
    }

    // Thêm mới gói tập
    @PostMapping("/add")
    public ResponseObject<?> addGoiTap(@RequestBody GoiTapDTO goiTapDTO) {
        var result = new ResponseObject<>();
        try {
            GoiTapE goiTapE = goiTapService.addGoiTap(goiTapDTO);
            result.setData(goiTapE);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Thêm mới gói tập thành công.");
        } catch (Exception e) {
            log.error("Lỗi khi thêm mới gói tập", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi thêm mới gói tập.");
        }
        return result;
    }

    // Cập nhật gói tập
    @PutMapping("/update/{maGoiTap}")
    public ResponseObject<?> updateGoiTap(@PathVariable int maGoiTap, @RequestBody GoiTapDTO goiTapDTO) {
        var result = new ResponseObject<>();
        try {
            GoiTapE updatedGoiTap = goiTapService.updateGoiTap(maGoiTap, goiTapDTO);
            if (updatedGoiTap != null) {
                result.setData(updatedGoiTap);
                result.setStatus(BasicApiConstant.SUCCEED.getStatus());
                result.setMessages("Cập nhật gói tập thành công.");
            } else {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Gói tập không tồn tại.");
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật gói tập", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi cập nhật gói tập.");
        }
        return result;
    }

    // Xóa gói tập (xóa mềm)
    @DeleteMapping("/delete/{maGoiTap}")
    public ResponseObject<?> deleteGoiTap(@PathVariable int maGoiTap) {
        var result = new ResponseObject<>();
        try {
            goiTapService.deleteGoiTap(maGoiTap);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Xóa gói tập thành công.");
        } catch (Exception e) {
            log.error("Lỗi khi xóa gói tập", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi xóa gói tập.");
        }
        return result;
    }
}
