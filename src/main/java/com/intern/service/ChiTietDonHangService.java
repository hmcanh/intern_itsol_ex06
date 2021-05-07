package com.intern.service;

import com.intern.dao.ChiTietDatHangDAO;
import com.intern.dto.ChiTietDatHangDTO;
import com.intern.dto.NhanVienDTO;
import com.intern.entity.ChiTietDatHangBO;
import com.intern.entity.NhanVienBO;
import com.intern.mapper.ChiTietDatHangMapper;
import com.intern.repository.ChiTietDonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class ChiTietDonHangService {
    @Autowired
    public   ChiTietDatHangMapper chiTietDatHangMapper;
    @Autowired
    public  ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    public ChiTietDatHangDAO chiTietDatHangDAO;

    public  List<ChiTietDatHangDTO> getChiTietDatHang(){
        List<ChiTietDatHangBO> bos = chiTietDonHangRepository.findAll();
        return  chiTietDatHangMapper.toDto(bos);
    }
    public List<ChiTietDatHangDTO> getChiTietDatHangNotSale(){
        return chiTietDatHangDAO.getChiTietDatHangNotSale();
    }
    public  void addChiTietDatHang(ChiTietDatHangDTO chiTietDatHangDTO) {
        ChiTietDatHangBO bo = chiTietDatHangMapper.toEntity(chiTietDatHangDTO);
        chiTietDonHangRepository.save(bo);
    }
    public  void updateChiTietDatHang (ChiTietDatHangDTO chiTietDatHangDTO , int SOHOADON){
        Optional <ChiTietDatHangBO> nv1 =  chiTietDonHangRepository.findById(SOHOADON);
     if ( nv1.isPresent()) {
         nv1.get().setGIABAN(chiTietDatHangDTO.getGIABAN());
         nv1.get().setMAHANG(chiTietDatHangDTO.getMAHANG());
         nv1.get().setMUCGIAMGIA(chiTietDatHangDTO.getMUCGIAMGIA());
         nv1.get().setSOHOADON(chiTietDatHangDTO.getSOHOADON());
         nv1.get().setSOLUONG(chiTietDatHangDTO.getSOLUONG());
         chiTietDonHangRepository.save(nv1.get());
     }
    }
    public ChiTietDatHangDTO findById( int SOHOADON){
        Optional<ChiTietDatHangBO> boOpt= chiTietDonHangRepository.findById(SOHOADON);
        boOpt.isPresent();
        return chiTietDatHangMapper.toDto(boOpt.get());
    }
    public  void  deleteChiTietDatHang( int SOHOADON) {
        chiTietDonHangRepository.deleteById(SOHOADON);
    }

}