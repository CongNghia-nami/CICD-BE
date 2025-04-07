package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api-public/registration")
public class HoaDonAPI {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping
    public ResponseObject<?> getAllHoaDon() {
        var result = new ResponseObject<>();
        try {
            List<HoaDonE> row = hoaDonService.findAll();
            result.setData(row);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages(BasicApiConstant.SUCCEED.name());

        } catch (Exception e) {
            log.info("fail when call api /api-public/registration", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @GetMapping("{id}")
    public ResponseObject<?> getHoaDonById(@PathVariable int maHoaDon) {
        var result = new ResponseObject<>();
        try {
            Optional<HoaDonE> data = hoaDonService.findHoaDonById(maHoaDon);
            result.setMessages(BasicApiConstant.SUCCEED.name());
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setData(data);
        } catch (Exception e) {
            log.info("fail when call api /api-public/registration" + maHoaDon, e);
            throw new RuntimeException(e);
        }
        return result;
    }


    @PostMapping
    public ResponseObject<Object> saveHoaDon(@RequestBody DangKyDTO registration) {
        var result = new ResponseObject<>();
        try {

            HoaDonE invoice = hoaDonService.saveHoaDon(registration);
            result.setData(invoice);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages(BasicApiConstant.SUCCEED.name());
        } catch (Exception e) {
            log.info("fail when call api /api-public/registration", e);
        }
        return result;
    }
}
