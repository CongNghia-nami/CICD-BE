package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;

public interface QrCodeService {
    String GenerateQrCode(ThanhVienE thanhVienE);
    String readQRCode(BufferedImage bufferedImage);
    String readQRCode (MultipartFile qrCodeFile);
    String readQRCode(String base64Image);
}
