package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class ThuHuong implements Serializable {

    private long MaSoThe;
    private long TKThuHuong;
    private String TenNguoiThuHuong;

    public ThuHuong() {

    }

    public ThuHuong(long maSoThe, long TKThuHuong, String tenNguoiThuHuong) {
        MaSoThe = maSoThe;
        this.TKThuHuong = TKThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
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
