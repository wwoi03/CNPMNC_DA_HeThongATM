package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class ThuHuong implements Serializable {
    private String Key;
    private String MaKHKey;
    private long TKThuHuong;
    private String TenNguoiThuHuong;

    public ThuHuong() {

    }

    public ThuHuong(String key, String maKHKey, long TKThuHuong, String tenNguoiThuHuong) {
        Key = key;
        MaKHKey = maKHKey;
        this.TKThuHuong = TKThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getMaKHKey() {
        return MaKHKey;
    }

    public void setMaKHKey(String maKHKey) {
        MaKHKey = maKHKey;
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
