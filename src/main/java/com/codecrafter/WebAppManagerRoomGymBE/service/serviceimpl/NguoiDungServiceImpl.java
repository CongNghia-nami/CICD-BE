package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.CustomUserDetailService;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.NguoiDungRepo;

import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {
    @Autowired
    private NguoiDungRepo nguoiDungRepository;
    @Autowired
    private NguoiDungRepo nguoiDungRepo;

    @Override
    public Optional<NguoiDungE> login(NguoiDungDTO userDTO) {
        Optional<NguoiDungE> user = nguoiDungRepository.findByTenNguoiDung(userDTO.getTenNguoiDung());
        return user;
    }

    @Override
    public Optional<NguoiDungE> findByUserName(String userName) {
        Optional<NguoiDungE> user = nguoiDungRepository.findByTenNguoiDung(userName);
        return user;
    }

    @Override
    public Optional<NguoiDungE> getUserInfo(int id) {
        return nguoiDungRepository.findById(id);
    }


    //        public boolean checkPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
   @Override
    public void softDeleteUser(int id) {
        Optional<NguoiDungE> user = nguoiDungRepository.findById(id);
        if (user.isPresent()) {
            NguoiDungE nguoiDung = user.get();
            nguoiDung.setTrangThaiNguoiDung(false); // Cập nhật trạng thái xóa mềm
            nguoiDungRepository.save(nguoiDung); // Lưu lại thay đổi
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    @Override
    public List<NguoiDungE> getAllNguoiDung() {
        return nguoiDungRepo.findAll();
    }


}






