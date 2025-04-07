package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;

public interface SendMailService {
   void sendEmail(ThanhVienDTO thanhVienDTO);
    void sendEmail(ThanhVienDTO thanhVienDTO, String subject, String message); //

}
