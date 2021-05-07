package com.intern.mapper;

import com.intern.dto.NhanVienDTO;
import com.intern.entity.NhanVienBO;
import  org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface NhanVienMapper extends EntityMapper<NhanVienDTO, NhanVienBO> {

}
