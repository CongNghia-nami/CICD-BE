package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.QrCodeService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-public/qrcode")
public class GenerateQrCodeAPI {

    private final QrCodeService qrCodeService;

    @PostMapping("/generateQRCode")
    public ResponseObject<?> GenerateQrCode(@RequestBody ThanhVienE thanhVienE) {
        var result = new ResponseObject<>();
        try {
            result.setData(qrCodeService.GenerateQrCode(thanhVienE));
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Generate QRCode thành công");
        } catch (Exception e) {
            result.setStatus(BasicApiConstant.FAILED.getStatus());
            log.error("fail when call api generate QR");
        }
        return result;
    }

    @PostMapping("/importFileEncodeQR")
    public ResponseObject<?> readFromImageQR(@RequestParam("file") MultipartFile file) {
        var result = new ResponseObject<>();
        try {
            result.setData(qrCodeService.readQRCode(file));
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("call api read from image QR");
        } catch (Exception e) {
            log.error("fail when call api import file read from image QR");
            result.setStatus(BasicApiConstant.FAILED.getStatus());
        }
        return result;
    }

}
