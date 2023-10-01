package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class Beneficiary implements Serializable {

    private String IdThuHuong;
    private long MaSoThe;
    private long TKThuHuong;
    private String TenNguoiThuHuong;

    public Beneficiary() {

    }

    public Beneficiary(String idThuHuong, long maSoThe, long TKThuHuong, String tenNguoiThuHuong) {
        IdThuHuong = idThuHuong;
        MaSoThe = maSoThe;
        this.TKThuHuong = TKThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
    }

    public String getIdThuHuong() {
        return IdThuHuong;
    }

    public void setIdThuHuong(String idThuHuong) {
        IdThuHuong = idThuHuong;
    }

    public long getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(long maSoThe) {
        MaSoThe = maSoThe;
    }

    public long getTKThuHuong() {
        return TKThuHuong;
    }

    public void setTKThuHuong(long TKThuHuong) {
        this.TKThuHuong = TKThuHuong;
    }

    public String getTenNguoiThuHuong() {
        return TenNguoiThuHuong;
    }

    public void setTenNguoiThuHuong(String tenNguoiThuHuong) {
        TenNguoiThuHuong = tenNguoiThuHuong;
    }
}
