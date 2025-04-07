
package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LopHocDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.LopHocRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.LopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LopHocServiceImpl implements LopHocService {
    @Autowired
    private LopHocRepo lopHocRepo;
    @Autowired
    private DangKyRepo dangKyRepo;


     @Override
    public List<LopHocM> getAllLopHoc() {
        List<LopHocE> lopHocEList = lopHocRepo.findAll();
        return LopHocM.convertListLopHocEToLopHocM(lopHocEList);
    }

    @Override
    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien) {
        List<LopHocE> lopHocEList = lopHocRepo.findByMaThanhVien(maThanhVien);
        return LopHocM.convertListLopHocEToLopHocM(lopHocEList);
    }


    @Override
    public LopHocE addLopHoc(LopHocDTO lopHocDTO) {
        LopHocE lopHocE = new LopHocE();
        lopHocE.setTenLopHoc(lopHocDTO.getTenLopHoc());
        lopHocE.setMoTaLopHoc(lopHocDTO.getMoTaLopHoc());
        lopHocE.setGiaLopHoc(lopHocDTO.getGiaLopHoc());
        lopHocE.setLichHoc(lopHocDTO.getLichHoc());
        lopHocE.setSoLuongThanhVienLopHoc(lopHocDTO.getSoLuongThanhVienLopHoc());
        lopHocE.setTrangThaiLopHoc(true);
        return lopHocRepo.save(lopHocE);
    }

    @Override
    public LopHocE updateLopHoc(int maLopHoc, LopHocDTO lopHocDTO) {
        Optional<LopHocE> lopHocOpt = lopHocRepo.findById(maLopHoc);
        if (lopHocOpt.isPresent()) {
            LopHocE lopHocE = lopHocOpt.get();
            lopHocE.setTenLopHoc(lopHocDTO.getTenLopHoc());
            lopHocE.setMoTaLopHoc(lopHocDTO.getMoTaLopHoc());
            lopHocE.setGiaLopHoc(lopHocDTO.getGiaLopHoc());
            lopHocE.setLichHoc(lopHocDTO.getLichHoc());
            lopHocE.setSoLuongThanhVienLopHoc(lopHocDTO.getSoLuongThanhVienLopHoc());
            lopHocE.setTrangThaiLopHoc(lopHocDTO.isTrangThaiLopHoc());
            return lopHocRepo.save(lopHocE);
        }
        return null;
    }

  @Override
public void deleteLopHoc(int maLopHoc) {
    Optional<LopHocE> lopHocOpt = lopHocRepo.findById(maLopHoc);
    if (lopHocOpt.isPresent()) {
        LopHocE lopHocE = lopHocOpt.get();
        lopHocE.setTrangThaiLopHoc(false);  // Set trangThaiLopHoc to false
        lopHocRepo.save(lopHocE);  // Save the updated LopHocE
    }
}

}
