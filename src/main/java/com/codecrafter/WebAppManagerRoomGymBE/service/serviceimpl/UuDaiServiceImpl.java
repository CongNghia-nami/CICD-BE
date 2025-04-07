package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.UuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.UuDaiService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UuDaiServiceImpl implements UuDaiService {

    @Autowired
    private UuDaiRepo uuDaiRepository;

    @Override
    public UuDaiE createUuDai(UuDaiDTO uuDai) {
        UuDaiE uuDaiE = UuDaiE.builder()
                .moTaUuDai(uuDai.getMoTaUuDai())
                .giaTriUuDai(uuDai.getGiaTriUuDai())
                .ngayBatDauUuDai(uuDai.getNgayBatDauUuDai())
                .ngayKetThucUuDai(uuDai.getNgayKetThucUuDai())
                .trangThaiUuDai(uuDai.isTrangThaiUuDai())
                .build();
        return uuDaiRepository.save(uuDaiE);
    }

    @Override
    public UuDaiE getUuDaiById(int id) {
        return uuDaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
    }

    @Override
    public List<UuDaiE> getAllUuDais() {
        List<UuDaiE> listUuDai = uuDaiRepository.findAll();
        for (UuDaiE uuDai :listUuDai)
        {
            uuDai.setTongUuDai(listUuDai.size());
        }
        return uuDaiRepository.findAll();
    }

    @Override
    public List<UuDaiE> getActiveUuDais() {
        return uuDaiRepository.findByTrangThaiUuDai(true);
    }

    @Override
    public UuDaiE updateUuDai(int id, UuDaiDTO uuDai) {
        UuDaiE existingUuDai = getUuDaiById(id);
        existingUuDai.setMoTaUuDai(uuDai.getMoTaUuDai());
        existingUuDai.setNgayBatDauUuDai(uuDai.getNgayBatDauUuDai());
        existingUuDai.setNgayKetThucUuDai(uuDai.getNgayKetThucUuDai());
        existingUuDai.setGiaTriUuDai(uuDai.getGiaTriUuDai());
        existingUuDai.setTrangThaiUuDai(uuDai.isTrangThaiUuDai());
        return uuDaiRepository.save(existingUuDai);
    }

    @Override
    public UuDaiE deleteUuDai(int id) {
        UuDaiE existingUuDai = getUuDaiById(id);
        existingUuDai.setTrangThaiUuDai(false);
        return uuDaiRepository.save(existingUuDai);
    }

    @Override
    public List<UuDaiE> getUuDaiByIdAndOtherParam(Integer maUuDai, Date ngayBatDau, Date ngayKetThuc, Boolean trangThaiUuDai) {
        List<UuDaiE> listUuDai = uuDaiRepository.findAll((Root<UuDaiE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Kiểm tra từng tham số và thêm điều kiện tương ứng nếu tham số không null
            if (maUuDai != null) {
                predicates.add(cb.equal(root.get("maUuDai"), maUuDai));
            }
            if (ngayBatDau != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ngayBatDauUuDai"), ngayBatDau));
            }
            if (ngayKetThuc != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ngayKetThucUuDai"), ngayKetThuc));
            }
            if (trangThaiUuDai != null) {
                predicates.add(cb.equal(root.get("trangThaiUuDai"), trangThaiUuDai));
            }

            // Kết hợp các điều kiện với AND và trả về
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        for (UuDaiE uuDai : listUuDai) {
            uuDai.setTongUuDai(listUuDai.size());
        }
        return listUuDai;
    }

}
