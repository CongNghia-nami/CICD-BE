package com.codecrafter.WebAppManagerRoomGymBE.service;

import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface DoanhThuService {
     Map<String, Object> getDoanhThuDetails(Integer day, Integer month, Integer year);

}
