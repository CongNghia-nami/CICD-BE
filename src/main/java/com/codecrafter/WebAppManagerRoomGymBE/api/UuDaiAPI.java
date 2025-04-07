package com.codecrafter.WebAppManagerRoomGymBE.api;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.service.UuDaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api-public/uu-dais")
public class UuDaiAPI {

    @Autowired
    private UuDaiService uuDaiService;

    // Create a new discount
    @PostMapping
    public ResponseEntity<UuDaiE> createUuDai(@RequestBody UuDaiDTO uuDai) {
        UuDaiE createdUuDai = uuDaiService.createUuDai(uuDai);
        return ResponseEntity.ok(createdUuDai);
    }

    // Get a discount by ID
    @GetMapping("/{id}")
    public ResponseEntity<UuDaiE> getUuDaiById(@PathVariable int id) {
        UuDaiE uuDai = uuDaiService.getUuDaiById(id);
        return ResponseEntity.ok(uuDai);
    }

    // Get all discounts
    @GetMapping
    public ResponseEntity<List<UuDaiE>> getAllUuDais() {
        List<UuDaiE> uuDais = uuDaiService.getAllUuDais();
        return ResponseEntity.ok(uuDais);
    }

    // Get all active discounts
    @GetMapping("/active")
    public ResponseEntity<List<UuDaiE>> getActiveUuDais() {
        List<UuDaiE> activeUuDais = uuDaiService.getActiveUuDais();
        return ResponseEntity.ok(activeUuDais);
    }

    // Update a discount by ID
    @PutMapping("/{id}")
    public ResponseEntity<UuDaiE> updateUuDai(
            @PathVariable int id, @RequestBody UuDaiDTO uuDaiDTO) {
        UuDaiE updatedUuDai = uuDaiService.updateUuDai(id, uuDaiDTO);
        return ResponseEntity.ok(updatedUuDai);
    }

    // Delete a discount by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUuDai(@PathVariable int id) {
        uuDaiService.deleteUuDai(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<UuDaiE>> getUuDaiByIdAndOtherParam(
            @RequestParam(required = false) Integer maUuDai,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKetThuc,
            @RequestParam(required = false) Boolean trangThaiUuDai) {

        List<UuDaiE> uuDais = uuDaiService.getUuDaiByIdAndOtherParam(maUuDai, ngayBatDau, ngayKetThuc, trangThaiUuDai);
        return ResponseEntity.ok(uuDais);
    }
}

