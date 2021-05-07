package com.intern.mapper;

import com.intern.dto.ChiTietDatHangDTO;
import com.intern.entity.ChiTietDatHangBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChiTietDatHangMapper extends EntityMapper<ChiTietDatHangDTO,ChiTietDatHangBO> {
}
