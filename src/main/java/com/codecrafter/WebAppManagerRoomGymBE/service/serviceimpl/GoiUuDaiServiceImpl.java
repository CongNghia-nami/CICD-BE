package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiUuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiTapRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiUuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.UuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiUuDaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoiUuDaiServiceImpl implements GoiUuDaiService {
    @Autowired
    private GoiUuDaiRepo goiUuDaiRepo;
    @Autowired
    private GoiTapRepo goiTapRepo;

    @Autowired
    private UuDaiRepo uuDaiRepo;

    @Override
    public List<GoiUuDaiE> getGoiUuDai() {
        return goiUuDaiRepo.findAll();
    }

    @Override
    public List<GoiUuDaiE> getGoiUuDaiByThanhVien(int maThanhVien) {
        return goiUuDaiRepo.findByMaThanhVien(maThanhVien);
    }

    @Override
    public GoiUuDaiE createGoiUuDai(GoiUuDaiDTO dto) {
        GoiUuDaiE goiUuDai = new GoiUuDaiE();
        GoiTapE goiTap = goiTapRepo.findById(dto.getMaGoiTap())
                .orElseThrow(() -> new RuntimeException("Gói tập không tồn tại."));
        goiUuDai.setGoiTap(goiTap);

        if (dto.getMaUuDai() != null) {
            UuDaiE uuDai = uuDaiRepo.findById(dto.getMaUuDai())
                    .orElseThrow(() -> new RuntimeException("Ưu đãi không tồn tại."));
            goiUuDai.setUuDai(uuDai);
        } else {
            goiUuDai.setUuDai(null);
        }

        return goiUuDaiRepo.save(goiUuDai);
    }

    @Override
    public GoiUuDaiE updateGoiUuDai(int id, GoiUuDaiDTO dto) {
        GoiUuDaiE existingGoiUuDai = goiUuDaiRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gói ưu đãi không tồn tại."));
        GoiTapE goiTap = goiTapRepo.findById(dto.getMaGoiTap())
                .orElseThrow(() -> new RuntimeException("Gói tập không tồn tại."));
        existingGoiUuDai.setGoiTap(goiTap);

        if (dto.getMaUuDai() != null) {
            UuDaiE uuDai = uuDaiRepo.findById(dto.getMaUuDai())
                    .orElseThrow(() -> new RuntimeException("Ưu đãi không tồn tại."));
            existingGoiUuDai.setUuDai(uuDai);
        } else {
            existingGoiUuDai.setUuDai(null);
        }

        return goiUuDaiRepo.save(existingGoiUuDai);
    }

    @Override
    public void deleteGoiUuDai(int id) {
        if (!goiUuDaiRepo.existsById(id)) {
            throw new RuntimeException("Gói ưu đãi không tồn tại.");
        }
        goiUuDaiRepo.deleteById(id);
    }
}
