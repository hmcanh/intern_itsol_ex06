package com.intern.rest;

import com.intern.dto.NhanVienDTO;
import com.intern.mapper.NhanVienMapper;
import com.intern.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeRest {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private NhanVienMapper nhanVienMapper;


    @GetMapping("/employee")
    public ResponseEntity<List<NhanVienDTO>> getEmployee() {
        List<NhanVienDTO> dataReponse = nhanVienService.getEmployees();
        return ResponseEntity.ok(dataReponse);
    }

    @GetMapping("/employees-not-sales")
    public ResponseEntity<List<NhanVienDTO>> getEmpNotSales() {
        List<NhanVienDTO> dataResponse = nhanVienService.getEmpNotSales();
        return ResponseEntity.ok(dataResponse);
    }
@GetMapping( "/{maNhanVien}")
public ResponseEntity<NhanVienDTO>findById( @PathVariable String maNhanVien) {
        NhanVienDTO boOpt = nhanVienService.findById(maNhanVien) ;
        return ResponseEntity.ok(boOpt);
}
    @PostMapping("/{maNhanVien}")
    public String update( @RequestBody NhanVienDTO nhanVienDTO,@PathVariable String maNhanVien) {
        nhanVienService.updateEmployee(nhanVienDTO, maNhanVien);
        return  "update successfull";
    }
    @PostMapping ( "/{maNhanVien")
    public  String deleteEmploy ( @PathVariable String maNhanVien) {
        nhanVienService.delete(maNhanVien);
        return " delete succesfull";
    }

}
