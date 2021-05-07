package com.intern.rest;

import com.intern.dto.ChiTietDatHangDTO;
import com.intern.dto.NhanVienDTO;
import com.intern.mapper.NhanVienMapper;
import com.intern.service.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class ChiTietDonHangRest {
    @Autowired
    public ChiTietDonHangService chiTietDonHangService;
    @Autowired
    public NhanVienMapper nhanVienMapper;

    @GetMapping( "/chitiet")
public ResponseEntity<List<ChiTietDatHangDTO>> getChiTietDonHang() {
    List<ChiTietDatHangDTO> dataResponse = chiTietDonHangService.getChiTietDatHang();
    return ResponseEntity.ok(dataResponse);
}



}
