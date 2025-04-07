package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.service.QrCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final int height = 300;
    private static final int weight = 300;

    private Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);


    @Override
    public String GenerateQrCode(ThanhVienE thanhVienE) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder  result = new StringBuilder();
        String qrCodeContent = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            qrCodeContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(thanhVienE);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, weight, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage,"png",os);

            result.append("data:image/png;base64,");
            result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
        } catch (WriterException e) {
            logger.error("Error", e);
        } catch (IOException e) {
            logger.error("Error", e);
        }
        return result.toString();
    }

    @Override
    public String readQRCode(BufferedImage bufferedImage) {
        String encodeContent = null;

        try {
            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();

            Result result = multiFormatReader.decode(binaryBitmap);
            encodeContent = result.getText();
        } catch (Exception e) {
            System.out.println("fail when decode QR");
            e.printStackTrace();
        }
        return encodeContent;
    }

    @Override
    public String readQRCode(MultipartFile qrCodeFile) {
        String encodedContent = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile.getInputStream());
            encodedContent = readQRCode(bufferedImage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encodedContent;
    }

    @Override
    public String readQRCode(String base64Image) {
        String encodedContent = null;
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image.getBytes());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            encodedContent = readQRCode(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedContent;
    }

}
