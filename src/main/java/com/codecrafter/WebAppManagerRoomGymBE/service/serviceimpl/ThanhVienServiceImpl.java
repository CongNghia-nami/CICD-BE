package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.NguoiDungRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

import static com.codecrafter.WebAppManagerRoomGymBE.utils.getCurrentUser.getCurrentUserId;

@Service
public class ThanhVienServiceImpl implements ThanhVienService {
    @Autowired
    private ThanhVienRepo thanhVienRepository;
    @Autowired
    private NguoiDungRepo nguoiDungRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private DangKyRepo dangKyRepo;

    private static final String FILE_PATH = "KhachHang.xlsx";


    @Override
    public Optional<ThanhVienE> disableMember(int maThanhVien) {
        Optional<ThanhVienE> thanhVienOpt = thanhVienRepository.findById(maThanhVien);
        if (thanhVienOpt.isPresent()) {
            ThanhVienE thanhVien = thanhVienOpt.get();
            // Cập nhật trangThaiThanhVien thành false
            thanhVien.setTrangThaiThanhVien(false);
            thanhVienRepository.save(thanhVien); // Lưu lại cập nhật
            return Optional.of(thanhVien);
        }
        return Optional.empty(); // Nếu không tìm thấy thành viên
    }

    @Override
    public Optional<ThanhVienE> register(ThanhVienDTO userDTO) {
        // Kiểm tra email và số điện thoại
        boolean tenThanhVienExists = thanhVienRepository.existsByTenThanhVien(userDTO.getTenThanhVien());
        boolean emailExists = thanhVienRepository.existsByEmailThanhVien(userDTO.getEmailThanhVien());
        boolean soDienThoaiExists = thanhVienRepository.existsBySoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());

        if (tenThanhVienExists) {
            throw new IllegalArgumentException("Tên thành viên đã tồn tại. Vui lòng chọn tên khác.");
        } else if (emailExists && soDienThoaiExists) {
            throw new IllegalArgumentException("Email và số điện thoại đã tồn tại.");
        } else if (emailExists) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng chọn email khác.");
        } else if (soDienThoaiExists) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại. Vui lòng chọn số điện thoại khác.");
        }
        Long currentUserId = getCurrentUserId();
        NguoiDungE nguoiDung = nguoiDungRepo.findById(Math.toIntExact(currentUserId))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng hiện tại."));

        // Tạo đối tượng ThanhVienE mới
        ThanhVienE thanhVien = new ThanhVienE();
        thanhVien.setTenThanhVien(userDTO.getTenThanhVien());
        thanhVien.setEmailThanhVien(userDTO.getEmailThanhVien());
        thanhVien.setMatKhauNguoiDung(userDTO.getMatKhauNguoiDung()); // Mật khẩu chưa mã hóa
        thanhVien.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVien.setNgaySinhThanhVien(userDTO.getNgaySinhThanhVien());
        thanhVien.setTrangThaiThanhVien(userDTO.isTrangThaiThanhVien());
        thanhVien.setNguoiDung(nguoiDung);
        // Lưu lần đầu để lấy id
        ThanhVienE savedThanhVien = thanhVienRepository.save(thanhVien);

        // Tạo mã QR có chứa id
        String qrCodeData = qrCodeService.GenerateQrCode(savedThanhVien);
        savedThanhVien.setDuLieuQrDinhDanh(qrCodeData);

        // Cập nhật lại đối tượng với QR code
        thanhVienRepository.save(savedThanhVien);

        return Optional.of(savedThanhVien);
    }

    @Override
    public Optional<ThanhVienE> login(ThanhVienDTO memberDTO) {
        Optional<ThanhVienE> member = thanhVienRepository.findByTenThanhVien(memberDTO.getTenThanhVien());
        if (member.isPresent()) {
            ThanhVienE thanhVien = member.get();
            // Kiểm tra trạng thái thành viên
            if (!thanhVien.isTrangThaiThanhVien()) {
                return Optional.empty(); // Nếu thành viên bị khóa, không cho phép đăng nhập
            }
            // Kiểm tra mật khẩu
            if (passwordEncoder.matches(memberDTO.getMatKhauNguoiDung(), thanhVien.getMatKhauNguoiDung())) {
                return Optional.of(thanhVien);
            }
        }
        return Optional.empty(); // Nếu không khớp mật khẩu hoặc không tìm thấy thành viên
    }


    public Page<ThanhVienE> getAllMembers(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return thanhVienRepository.findAll(pageable);
    }

    public void exportToExcel(String name, int phone, String email) throws IOException {
        File existingFile = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        // Kiểm tra file đã tồn tại chưa
        if (existingFile.exists()) {
            // Mở file Excel đã tồn tại
            try (FileInputStream inputStream = new FileInputStream(existingFile)) {
                workbook = new XSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

                // Tìm dòng cuối cùng để thêm dữ liệu mới
                int lastRowNum = sheet.getLastRowNum();
                Row newRow = sheet.createRow(lastRowNum + 1);

                newRow.createCell(0).setCellValue(name);
                newRow.createCell(1).setCellValue(phone);
                newRow.createCell(2).setCellValue(email);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Nếu file chưa tồn tại, tạo mới
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Danh Sách Khách Hàng");

            // Tạo header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Tên", "Số Điện Thoại", "Email"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Thêm dữ liệu đầu tiên
            Row firstRow = sheet.createRow(1);
            firstRow.createCell(0).setCellValue(name);
            firstRow.createCell(1).setCellValue(phone);
            firstRow.createCell(2).setCellValue(email);
        }

        // Ghi dữ liệu và đóng file
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        }

        workbook.close();
    }

}
