package com.intern.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "chitietdathang")
public class ChiTietDatHangBO {
    @Id
    @Column
    private int SOHOADON;
    private  int MAHANG;
    private  int GIABAN;
    private int SOLUONG;
    private float MUCGIAMGIA;
 public ChiTietDatHangBO(){}

    public int getSOHOADON() {
        return SOHOADON;
    }

    public void setSOHOADON(int SOHOADON) {
        this.SOHOADON = SOHOADON;
    }

    public int getMAHANG() {
        return MAHANG;
    }

    public void setMAHANG(int MAHANG) {
        this.MAHANG = MAHANG;
    }

    public int getGIABAN() {
        return GIABAN;
    }

    public void setGIABAN(int GIABAN) {
        this.GIABAN = GIABAN;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public float getMUCGIAMGIA() {
        return MUCGIAMGIA;
    }

    public void setMUCGIAMGIA(float MUCGIAMGIA) {
        this.MUCGIAMGIA = MUCGIAMGIA;
    }
}
