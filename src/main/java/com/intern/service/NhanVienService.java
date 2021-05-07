package com.intern.service;

import com.intern.dao.NhanVienDAO;
import com.intern.dto.NhanVienDTO;
import com.intern.entity.NhanVienBO;
import com.intern.mapper.NhanVienMapper;
import com.intern.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {
    @Autowired
    private NhanVienDAO nhanVienDAO;

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private NhanVienMapper nhanVienMapper;


    public List<NhanVienDTO> getEmpNotSales() {
        return nhanVienDAO.getEmployeeNotSales();
    }

    public List<NhanVienDTO> getEmployees() {
        List<NhanVienBO> bos = nhanVienRepository.findAll();
        return nhanVienMapper.toDto(bos);
    }

    public void addEmployee(NhanVienDTO nhanVienDTO) {
        NhanVienBO bo = nhanVienMapper.toEntity(nhanVienDTO);
        nhanVienRepository.save(bo);

    }

    public void updateEmployee(NhanVienDTO nhanVienDTO, String maNhanVien) {
        Optional<NhanVienBO> nv1 = nhanVienRepository.findById(maNhanVien);
        if (nv1.isPresent()) {
            nv1.get().setDiaChi(nhanVienDTO.getDiaChi());
            nv1.get().setNgayLamViec(nhanVienDTO.getNgayLamViec());
            nv1.get().setNgaySinh(nhanVienDTO.getNgaySinh());
            nv1.get().setTen(nhanVienDTO.getTen());
            nv1.get().setHo(nhanVienDTO.getHo());
            nv1.get().setDiaChi(nhanVienDTO.getDiaChi());
            nv1.get().setLuongCoBan(nhanVienDTO.getLuongCoBan());
            nhanVienRepository.save(nv1.get());
        }
    }

    public NhanVienDTO findById(String maNhanVien) {
        Optional<NhanVienBO> boOpt = nhanVienRepository.findById(maNhanVien);
        boOpt.isPresent();
        return nhanVienMapper.toDto(boOpt.get());
    }

    public void delete(String maNhanVien) {
        nhanVienRepository.deleteById(maNhanVien);
    }

}