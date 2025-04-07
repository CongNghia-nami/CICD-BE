package com.codecrafter.WebAppManagerRoomGymBE.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRUtil {

    public static String GenerateQRCode(String data, int with, int height){
        StringBuilder readImages = new StringBuilder();

        if (data != null){
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,with,height);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage,"png",os);
                readImages.append("data:image/png;base64,");
                readImages.append(new String(Base64.getEncoder().encode(os.toByteArray())));

            } catch (Exception e) {
                System.out.println("fail when generate qr code ");
                e.printStackTrace();

            }
        }
        return readImages.toString();
    }

    public static String prettyObject(Object object){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            System.out.println("fail convert ob to str in prettyObject");
        }
        return "";
    }
}
