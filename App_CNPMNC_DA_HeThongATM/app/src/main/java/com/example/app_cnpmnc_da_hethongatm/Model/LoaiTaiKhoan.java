package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class LoaiTaiKhoan implements Serializable {
    private String Key;
    private String TenLoaiTaiKhoan;

    public LoaiTaiKhoan() {
    }

    public LoaiTaiKhoan(String key, String tenLoaiTaiKhoan) {
        Key = key;
        TenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTenLoaiTaiKhoan() {
        return TenLoaiTaiKhoan;
    }

    public void setTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
        TenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }
}
