package com.example.app_cnpmnc_da_hethongatm.Model;

public class LoaiTaiKhoan {
    private String TenLoaiTaiKhoan;

    public LoaiTaiKhoan() {
    }

    public LoaiTaiKhoan(String tenLoaiTaiKhoan) {
        TenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }

    public String getTenLoaiTaiKhoan() {
        return TenLoaiTaiKhoan;
    }

    public void setTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
        TenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }
}
