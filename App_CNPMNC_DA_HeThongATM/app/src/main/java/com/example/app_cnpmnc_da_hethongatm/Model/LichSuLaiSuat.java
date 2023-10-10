package com.example.app_cnpmnc_da_hethongatm.Model;

public class LichSuLaiSuat {
    private String MaGuiTietKiem;
    private double SoTienLai;
    private String NgayNhan;
    private double SoDuTaiKhoanNguon;

    public LichSuLaiSuat() {
    }

    public LichSuLaiSuat(String maGuiTietKiem, double soTienLai, String ngayNhan, double soDuTaiKhoanNguon) {
        MaGuiTietKiem = maGuiTietKiem;
        SoTienLai = soTienLai;
        NgayNhan = ngayNhan;
        SoDuTaiKhoanNguon = soDuTaiKhoanNguon;
    }

    public String getMaGuiTietKiem() {
        return MaGuiTietKiem;
    }

    public void setMaGuiTietKiem(String maGuiTietKiem) {
        MaGuiTietKiem = maGuiTietKiem;
    }

    public double getSoTienLai() {
        return SoTienLai;
    }

    public void setSoTienLai(double soTienLai) {
        SoTienLai = soTienLai;
    }

    public String getNgayNhan() {
        return NgayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        NgayNhan = ngayNhan;
    }

    public double getSoDuTaiKhoanNguon() {
        return SoDuTaiKhoanNguon;
    }

    public void setSoDuTaiKhoanNguon(double soDuTaiKhoanNguon) {
        SoDuTaiKhoanNguon = soDuTaiKhoanNguon;
    }
}
