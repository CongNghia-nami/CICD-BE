package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.*;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonChiTietService;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public HoaDonE getInvoiceDetails(int invoiceId) {
        return hoaDonRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public float calculateInvoiceAmount(int invoiceId) {
        HoaDonE invoice = getInvoiceDetails(invoiceId);

        List<DangKyE> listDangKy = invoice.getDangkys();

        // Biến để lưu tổng tiền
        float totalAmount = (float) listDangKy.stream().mapToDouble(dangKy ->{
                GoiUuDaiE goiUuDaiE =dangKy.getGoiUuDai();
                return (goiUuDaiE!=null) ? goiUuDaiE.getGoiTap().getGiaGoiTap(): 0;
        }).sum();

//        for (DangKyE dangKy : listDangKy) {
//            GoiUuDaiE goiUuDai = dangKy.getGoiUuDai();
//            if (goiUuDai != null) {
//                totalAmount += goiUuDai.getGoiTap().getGiaGoiTap();
//            }
//        }

        return totalAmount;
    }

}

