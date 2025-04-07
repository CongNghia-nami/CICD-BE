package com.codecrafter.WebAppManagerRoomGymBE.service;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import org.springframework.data.domain.Page;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ThanhVienService {
     Optional<ThanhVienE> register(ThanhVienDTO userDTO);
      Optional<ThanhVienE> login(ThanhVienDTO memberDTO);
    public Page<ThanhVienE> getAllMembers(int page, int size, String sortBy, boolean ascending);
        Optional<ThanhVienE> disableMember(int maThanhVien);
    public void exportToExcel(String name, int soDienThoai,String email) throws IOException;
}
