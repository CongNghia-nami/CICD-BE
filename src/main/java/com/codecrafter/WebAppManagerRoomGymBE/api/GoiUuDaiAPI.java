package com.codecrafter.WebAppManagerRoomGymBE.api;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiUuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiUuDaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/goi-uu-dai")
public class GoiUuDaiAPI {
    @Autowired
    private GoiUuDaiService goiUuDaiService;

    @GetMapping("/all")
    public ResponseEntity<List<GoiUuDaiE>> getAllGoiUuDai() {
        List<GoiUuDaiE> goiUuDais = goiUuDaiService.getGoiUuDai();
        return ResponseEntity.ok(goiUuDais);
    }

    @GetMapping("/thanh-vien/{maThanhVien}")
    public ResponseEntity<List<GoiUuDaiE>> getGoiUuDaiByThanhVien(@PathVariable int maThanhVien) {
        List<GoiUuDaiE> goiUuDais = goiUuDaiService.getGoiUuDaiByThanhVien(maThanhVien);
        return ResponseEntity.ok(goiUuDais);
    }
    @PostMapping("/save")
    public ResponseEntity<GoiUuDaiE> createGoiUuDai(@RequestBody GoiUuDaiDTO dto) {
        GoiUuDaiE created = goiUuDaiService.createGoiUuDai(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/updateGoi{id}")
    public ResponseEntity<GoiUuDaiE> updateGoiUuDai(@PathVariable int id, @RequestBody GoiUuDaiDTO dto) {
        GoiUuDaiE updated = goiUuDaiService.updateGoiUuDai(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteGoi{id}")
    public ResponseEntity<Void> deleteGoiUuDai(@PathVariable int id) {
        goiUuDaiService.deleteGoiUuDai(id);
        return ResponseEntity.noContent().build();
    }

}
