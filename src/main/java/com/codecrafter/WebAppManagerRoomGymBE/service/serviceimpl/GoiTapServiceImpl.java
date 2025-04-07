package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiTapDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiTapRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoiTapServiceImpl implements GoiTapService {
    @Autowired
    private GoiTapRepo goiTapRepo;

    // Lấy tất cả gói tập có trạng thái trangThaiGoiTap là true
    @Override
    public List<GoiTapE> getAllGoiTap() {
        return goiTapRepo.findAll();
    }

    // Lấy gói tập theo ID
    @Override
    public Optional<GoiTapE> getGoiTapById(int maGoiTap) {
        return goiTapRepo.findById(maGoiTap);
    }

    // Thêm mới gói tập
    @Override
    public GoiTapE addGoiTap(GoiTapDTO goiTapDTO) {
        GoiTapE goiTapE = new GoiTapE();
        goiTapE.setTenGoiTap(goiTapDTO.getTenGoiTap());
        goiTapE.setMoTaGoiTap(goiTapDTO.getMoTaGoiTap());
        goiTapE.setGiaGoiTap(goiTapDTO.getGiaGoiTap());
        goiTapE.setThoiHanGoiTap(goiTapDTO.getThoiHanGoiTap());
        goiTapE.setTrangThaiGoiTap(true); // Mặc định là true khi thêm mới
        return goiTapRepo.save(goiTapE);
    }

    // Cập nhật thông tin gói tập
    @Override
    public GoiTapE updateGoiTap(int maGoiTap, GoiTapDTO goiTapDTO) {
        Optional<GoiTapE> existingGoiTap = goiTapRepo.findById(maGoiTap);
        if (existingGoiTap.isPresent()) {
            GoiTapE updatedGoiTap = existingGoiTap.get();
            updatedGoiTap.setTenGoiTap(goiTapDTO.getTenGoiTap());
            updatedGoiTap.setMoTaGoiTap(goiTapDTO.getMoTaGoiTap());
            updatedGoiTap.setGiaGoiTap(goiTapDTO.getGiaGoiTap());
            updatedGoiTap.setThoiHanGoiTap(goiTapDTO.getThoiHanGoiTap());
            updatedGoiTap.setTrangThaiGoiTap(goiTapDTO.isTrangThaiGoiTap());
            return goiTapRepo.save(updatedGoiTap);
        }
        return null; // Trả về null nếu không tìm thấy gói tập
    }

    // Xóa mềm gói tập (cập nhật trạng thái trangThaiGoiTap từ true thành false)
    @Override
    public void deleteGoiTap(int maGoiTap) {
        Optional<GoiTapE> existingGoiTap = goiTapRepo.findById(maGoiTap);
        if (existingGoiTap.isPresent()) {
            GoiTapE goiTap = existingGoiTap.get();
            goiTap.setTrangThaiGoiTap(false); // Cập nhật trạng thái thành false
            goiTapRepo.save(goiTap); // Lưu lại thay đổi
        }
    }
}
