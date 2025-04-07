package com.codecrafter.WebAppManagerRoomGymBE.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration config;


     public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> placeholders, File qrImageFile) {
        try {
            Template t = config.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, placeholders);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            // Embed QR image if provided
            if (qrImageFile != null) {
                helper.addInline("qrCodeImage", qrImageFile);
            }

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", to);

        } catch (MessagingException | TemplateException | IOException e) {
            log.error("Failed to send email to {}", to, e);
        }
    }

    public void emailTemplate(String packageName, String packageDescription, double packagePrice,
                              String memberName, String email, String rawPassword,
                              String phoneNumber, Date birthDate, String qrData) {
        log.info("Sending email to: " + email);

        Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("packageName", packageName);
        placeholders.put("packageDescription", packageDescription);
        placeholders.put("packagePrice", packagePrice);
        placeholders.put("memberName", memberName);
        placeholders.put("email", email);
        placeholders.put("rawPassword", rawPassword);
        placeholders.put("phoneNumber", phoneNumber);
        placeholders.put("birthDate", birthDate);

        File qrImageFile = null;
        if (qrData != null && !qrData.isEmpty()) {
            qrImageFile = convertBase64ToImage(qrData);
            placeholders.put("qrImage", "cid:qrCodeImage");
        }

        // Gửi email với template
        sendEmailWithTemplate(email, "Thông tin đăng ký gói tập", "emailTemplate.ftl", placeholders, qrImageFile);

        if (qrImageFile != null && qrImageFile.exists()) {
            qrImageFile.delete(); // Cleanup temporary file
        }
    }

    private File convertBase64ToImage(String base64String) {
    try {
        // Loại bỏ tiền tố nếu tồn tại
        if (base64String.startsWith("data:image")) {
            base64String = base64String.substring(base64String.indexOf(",") + 1);
        }

        // Giải mã Base64
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decodedBytes));

        // Lưu tạm hình ảnh
        File qrImageFile = File.createTempFile("qr_code", ".png");
        ImageIO.write(bufferedImage, "png", qrImageFile);

        return qrImageFile;
    } catch (IllegalArgumentException e) {
        log.error("Invalid Base64 string", e);
        return null;
    } catch (IOException e) {
        log.error("Failed to convert Base64 to image", e);
        return null;
    }
}



}
