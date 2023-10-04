package com.example.app_cnpmnc_da_hethongatm.Model;

public class LichSuLaiSuat {
    private String MaGuiTietKiem;
    private long SoTienLai;
    private String NgayNhan;
    private long SoDuTaiKhoanNguon;

    public LichSuLaiSuat() {
    }

    public LichSuLaiSuat(String maGuiTietKiem, long soTienLai, String ngayNhan, long soDuTaiKhoanNguon) {
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

    public long getSoTienLai() {
        return SoTienLai;
    }

    public void setSoTienLai(long soTienLai) {
        SoTienLai = soTienLai;
    }

    public String getNgayNhan() {
        return NgayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        NgayNhan = ngayNhan;
    }

    public long getSoDuTaiKhoanNguon() {
        return SoDuTaiKhoanNguon;
    }

    public void setSoDuTaiKhoanNguon(long soDuTaiKhoanNguon) {
        SoDuTaiKhoanNguon = soDuTaiKhoanNguon;
    }
}
