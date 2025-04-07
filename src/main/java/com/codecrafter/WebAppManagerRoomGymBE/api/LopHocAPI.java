package com.codecrafter.WebAppManagerRoomGymBE.api;


import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LopHocDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.LopHocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-public/lophoc")
public class LopHocAPI {

    @Autowired
    private LopHocService lopHocService;

    @GetMapping("/getLopHocByThanhVien")
    public ResponseObject<?> doGetLopHocByThanhVien(@RequestParam int maThanhVien) {
        var result = new ResponseObject<>();

        try {
            result.setData(lopHocService.getLopHocByThanhVienId(maThanhVien));
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("get lop hoc by ma thanh vien " + maThanhVien + " thanh cong");
        } catch (Exception e) {
            log.info("fail when call api /api-public/lophoc/getLopHocByThanhVien", e);
        }
        return result;
    }

    @GetMapping("/getAllLopHoc")
    public ResponseObject<?> getAllLopHoc() {
        var result = new ResponseObject<>();
        try {
            result.setData(lopHocService.getAllLopHoc());
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Danh sách lớp học là: " + lopHocService.getAllLopHoc().size());
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả lớp học", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi lấy tất cả lớp học");
        }
        return result;
    }


    @PostMapping("/add")
    public ResponseObject<?> addLopHoc(@RequestBody LopHocDTO lopHocDTO) {
        var result = new ResponseObject<>();
        try {
            result.setData(lopHocService.addLopHoc(lopHocDTO));
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Thêm mới lớp học thành công.");
        } catch (Exception e) {
            log.error("Lỗi khi thêm mới lớp học", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi thêm mới lớp học.");
        }
        return result;
    }

    @PutMapping("/update/{maLopHoc}")
    public ResponseObject<?> updateLopHoc(@PathVariable int maLopHoc, @RequestBody LopHocDTO lopHocDTO) {
        var result = new ResponseObject<>();
        try {
            LopHocE updatedLopHoc = lopHocService.updateLopHoc(maLopHoc, lopHocDTO);
            if (updatedLopHoc != null) {
                result.setData(updatedLopHoc);
                result.setStatus(BasicApiConstant.SUCCEED.getStatus());
                result.setMessages("Cập nhật lớp học thành công.");
            } else {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Lớp học không tồn tại.");
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật lớp học", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi cập nhật lớp học.");
        }
        return result;
    }

    @DeleteMapping("/delete/{maLopHoc}")
    public ResponseObject<?> deleteLopHoc(@PathVariable int maLopHoc) {
        var result = new ResponseObject<>();
        try {
            lopHocService.deleteLopHoc(maLopHoc);  // Call the service to update trangThaiLopHoc
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Xóa lớp học thành công.");
        } catch (Exception e) {
            log.error("Lỗi khi xóa lớp học", e);
            result.setStatus(BasicApiConstant.ERROR.getStatus());
            result.setMessages("Lỗi khi xóa lớp học.");
        }
        return result;
    }

}
