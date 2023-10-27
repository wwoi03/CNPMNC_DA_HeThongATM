package com.example.app_cnpmnc_da_hethongatm.Model;

public class MauChuyenTien {
    private String Key;
    private String TenNguoiNhan;
    private long TaiKhoanNhan;
    private double SoTien;
    private String NoiDung;
    private String MaKHKey;

    public MauChuyenTien() {
    }

    public MauChuyenTien(String key, String tenNguoiNhan, long taiKhoanNhan, double soTien, String noiDung, String maKHKey) {
        Key = key;
        TenNguoiNhan = tenNguoiNhan;
        TaiKhoanNhan = taiKhoanNhan;
        SoTien = soTien;
        NoiDung = noiDung;
        MaKHKey = maKHKey;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTenNguoiNhan() {
        return TenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        TenNguoiNhan = tenNguoiNhan;
    }

    public long getTaiKhoanNhan() {
        return TaiKhoanNhan;
    }

    public void setTaiKhoanNhan(long taiKhoanNhan) {
        TaiKhoanNhan = taiKhoanNhan;
    }

    public double getSoTien() {
        return SoTien;
    }

    public void setSoTien(double soTien) {
        SoTien = soTien;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getMaKHKey() {
        return MaKHKey;
    }

    public void setMaKHKey(String maKHKey) {
        MaKHKey = maKHKey;
    }
}
