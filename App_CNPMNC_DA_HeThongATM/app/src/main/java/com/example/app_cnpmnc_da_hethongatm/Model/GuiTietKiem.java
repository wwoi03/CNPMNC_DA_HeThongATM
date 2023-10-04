package com.example.app_cnpmnc_da_hethongatm.Model;

public class GuiTietKiem {
    private long SoTaiKhoan;
    private long TaiKhoanNguon;
    private String NgayGui;
    private long SoTienGui;

    public GuiTietKiem() {
    }

    public GuiTietKiem(long soTaiKhoan, long taiKhoanNguon, String ngayGui, long soTienGui) {
        SoTaiKhoan = soTaiKhoan;
        TaiKhoanNguon = taiKhoanNguon;
        NgayGui = ngayGui;
        SoTienGui = soTienGui;
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

    public long getSoTienGui() {
        return SoTienGui;
    }

    public void setSoTienGui(long soTienGui) {
        SoTienGui = soTienGui;
    }
}
