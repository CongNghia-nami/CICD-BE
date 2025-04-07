package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;

public interface HoaDonChiTietService {
    HoaDonE getInvoiceDetails(int invoiceId);
    float calculateInvoiceAmount(int invoiceId);
}
