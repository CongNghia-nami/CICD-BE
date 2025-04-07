package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailImpl implements SendMailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(ThanhVienDTO thanhVienDTO) {
        String subject = "Thông tin đăng ký thành viên";
        String message = String.format("Tên thành viên: %s\nSố điện thoại: %d\nNgày sinh: %s\nMật khẩu: %s",
                thanhVienDTO.getTenThanhVien(),
                thanhVienDTO.getSoDienThoaiThanhVien(),
                thanhVienDTO.getNgaySinhThanhVien(),
                thanhVienDTO.getMatKhauNguoiDung());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(thanhVienDTO.getEmailThanhVien());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmail(ThanhVienDTO thanhVienDTO, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(thanhVienDTO.getEmailThanhVien());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
