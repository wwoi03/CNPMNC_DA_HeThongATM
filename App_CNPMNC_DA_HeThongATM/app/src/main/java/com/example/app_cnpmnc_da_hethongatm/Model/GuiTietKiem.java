package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class GuiTietKiem implements Serializable {
    private String Key;
    private String MaKHKey;
    private long TaiKhoanTietKiem; // số tài khoản tiết kiệm
    private long TaiKhoanNguon;
    private String LaiSuatKey; // mã lãi suất
    private String NgayGui;
    private double TienLaiToiKy;
    private double TienGui;

    public GuiTietKiem() {
    }

    public GuiTietKiem(String key, String maKHKey, long taiKhoanTietKiem, long taiKhoanNguon, String laiSuatKey, String ngayGui, double tienLaiToiKy, double tienGui) {
        Key = key;
        MaKHKey = maKHKey;
        TaiKhoanTietKiem = taiKhoanTietKiem;
        TaiKhoanNguon = taiKhoanNguon;
        LaiSuatKey = laiSuatKey;
        NgayGui = ngayGui;
        TienLaiToiKy = tienLaiToiKy;
        TienGui = tienGui;
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

    public long getTaiKhoanTietKiem() {
        return TaiKhoanTietKiem;
    }

    public void setTaiKhoanTietKiem(long taiKhoanTietKiem) {
        TaiKhoanTietKiem = taiKhoanTietKiem;
    }

    public long getTaiKhoanNguon() {
        return TaiKhoanNguon;
    }

    public void setTaiKhoanNguon(long taiKhoanNguon) {
        TaiKhoanNguon = taiKhoanNguon;
    }

    public String getLaiSuatKey() {
        return LaiSuatKey;
    }

    public void setLaiSuatKey(String laiSuatKey) {
        LaiSuatKey = laiSuatKey;
    }

    public String getNgayGui() {
        return NgayGui;
    }

    public void setNgayGui(String ngayGui) {
        NgayGui = ngayGui;
    }

    public double getTienLaiToiKy() {
        return TienLaiToiKy;
    }

    public void setTienLaiToiKy(double tienLaiToiKy) {
        TienLaiToiKy = tienLaiToiKy;
    }

    public double getTienGui() {
        return TienGui;
    }

    public void setTienGui(double tienGui) {
        TienGui = tienGui;
    }
}
