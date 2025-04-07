package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-public/thanh-toan")
public class ThanhToanAPI {

    @Autowired
    private ThanhToanService thanhToanService;

    @PostMapping("/create")
    public ResponseEntity<ThanhToanE> createPayment(@RequestBody ThanhToanDTO thanhToanDTO) {
        ThanhToanE newPayment = thanhToanService.createPayment(thanhToanDTO);
        return ResponseEntity.ok(newPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThanhToanE> getPaymentById(@PathVariable int id) {
        Optional<ThanhToanE> payment = thanhToanService.findPaymentById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ThanhToanE>> getAllPayments() {
        List<ThanhToanE> payments = thanhToanService.findAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThanhToanE> updatePayment(@PathVariable int id, @RequestBody ThanhToanDTO thanhToanDTO) {
        ThanhToanE updatedPayment = thanhToanService.updatePayment(id, thanhToanDTO);
        return ResponseEntity.ok(updatedPayment);
    }
    @GetMapping("/doGetThanhToanByParam")
    public List<ThanhToanE> getHoaDon(
            @RequestParam(required = false) Integer maHoaDon,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayThanhToan,
            @RequestParam(required = false) Float soTienThanhToan,
            @RequestParam(required = false) String phuongThucThanhToan) {
        return thanhToanService.getHoaDonByParams(maHoaDon, ngayThanhToan, soTienThanhToan, phuongThucThanhToan);
    }
}
