package com.example.app_cnpmnc_da_hethongatm.Model;

public class GuiTietKiem {
    private long SoTaiKhoan; // số tài khoản tiết kiệm
    private long TaiKhoanNguon;
    private String NgayGui;
    private String KyHan; // mã lãi suất

    public GuiTietKiem() {
    }

    public GuiTietKiem(long soTaiKhoan, long taiKhoanNguon, String ngayGui, String kyHan) {
        SoTaiKhoan = soTaiKhoan;
        TaiKhoanNguon = taiKhoanNguon;
        NgayGui = ngayGui;
        KyHan = kyHan;
    }

    public long getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public long getTaiKhoanNguon() {
        return TaiKhoanNguon;
    }

    public void setTaiKhoanNguon(long taiKhoanNguon) {
        TaiKhoanNguon = taiKhoanNguon;
    }

    public String getNgayGui() {
        return NgayGui;
    }

    public void setNgayGui(String ngayGui) {
        NgayGui = ngayGui;
    }

    public String getKyHan() {
        return KyHan;
    }

    public void setKyHan(String kyHan) {
        KyHan = kyHan;
    }
}
