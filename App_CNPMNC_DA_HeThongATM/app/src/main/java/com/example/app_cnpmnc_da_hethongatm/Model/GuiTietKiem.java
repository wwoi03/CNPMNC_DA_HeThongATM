package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class GuiTietKiem implements Serializable {
    private String Key;
    private long TaiKhoanTietKiem; // số tài khoản tiết kiệm
    private long TaiKhoanNguon;
    private String LaiSuatKey; // mã lãi suất
    private String NgayGui;
    private double TienLaiToiKy;
    private double TienGui;

    public GuiTietKiem(String Key, long TaiKhoanTietKiem, long TaiKhoanNguon, String LaiSuatKey,
                       String NgayGui, double TienLaiToiKy, double TienGui) {
        this.Key = Key;
        this.TaiKhoanTietKiem = TaiKhoanTietKiem;
        this.TaiKhoanNguon = TaiKhoanNguon;
        this.LaiSuatKey = LaiSuatKey;
        this.NgayGui = NgayGui;
        this.TienLaiToiKy = TienLaiToiKy;
        this.TienGui = TienGui;
    }



    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
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
