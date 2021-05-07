package com.intern.dao;

import com.intern.dto.ChiTietDatHangDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ChiTietDatHangDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public List<ChiTietDatHangDTO> getChiTietDatHangNotSale(){
        StringBuilder sql = new StringBuilder();
        sql.append(" Select");
        sql.append(" NV.MANHANVIEN, ");
        sql.append(" NV.HO, ");
        sql.append(" NV.TEN");
        sql.append(" FROM NHANVIEN NV");
        sql.append(" WHERE NOT EXISTS (SELECT DDH.MANHANVIEN FROM DONDATHANG DDH WHERE NV.MANHANVIEN = DDH.MANHANVIEN )");
        Query query = entityManager.createNativeQuery(sql.toString(), "emp_not_sales_mapping");
        return query.getResultList();
    }
}
