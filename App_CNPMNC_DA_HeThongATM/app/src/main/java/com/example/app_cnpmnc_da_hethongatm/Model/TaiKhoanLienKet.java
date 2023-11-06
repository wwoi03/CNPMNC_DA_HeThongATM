package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class TaiKhoanLienKet implements Serializable {
    private String Key;
    private String MaKHKey;
    private String MaLoaiTKKey;
    private long SoTaiKhoan;
    private double SoDu;
    private String TenTK;
    private int TinhTrangTaiKhoan;
    private double HanMucTK;
    private String NgayGD;
    private double TienDaGD;
    private double TienGD1Lan;

    public TaiKhoanLienKet() {
    }

    public TaiKhoanLienKet(String key, String maKHKey, String maLoaiTKKey, long soTaiKhoan, double soDu, String tenTK, int tinhTrangTaiKhoan, double hanMucTK, String ngayGD, double tienDaGD, double tienGD1Lan) {
        Key = key;
        MaKHKey = maKHKey;
        MaLoaiTKKey = maLoaiTKKey;
        SoTaiKhoan = soTaiKhoan;
        SoDu = soDu;
        TenTK = tenTK;
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
        HanMucTK = hanMucTK;
        NgayGD = ngayGD;
        TienDaGD = tienDaGD;
        TienGD1Lan = tienGD1Lan;
    }

    public TaiKhoanLienKet  (long soTaiKhoan,  double soDu, String key){
        SoTaiKhoan = soTaiKhoan;
        SoDu = soDu;
        Key = key;
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

    public String getMaLoaiTKKey() {
        return MaLoaiTKKey;
    }

    public void setMaLoaiTKKey(String maLoaiTKKey) {
        MaLoaiTKKey = maLoaiTKKey;
    }

    public long getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public double getSoDu() {
        return SoDu;
    }

    public void setSoDu(double soDu) {
        SoDu = soDu;
    }

    public String getTenTK() {
        return TenTK;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
    }

    public int getTinhTrangTaiKhoan() {
        return TinhTrangTaiKhoan;
    }

    public void setTinhTrangTaiKhoan(int tinhTrangTaiKhoan) {
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
    }

    public double getHanMucTK() {
        return HanMucTK;
    }

    public void setHanMucTK(double hanMucTK) {
        HanMucTK = hanMucTK;
    }

    public String getNgayGD() {
        return NgayGD;
    }

    public void setNgayGD(String ngayGD) {
        NgayGD = ngayGD;
    }

    public double getTienDaGD() {
        return TienDaGD;
    }

    public void setTienDaGD(double tienDaGD) {
        TienDaGD = tienDaGD;
    }

    public double getTienGD1Lan() {
        return TienGD1Lan;
    }

    public void setTienGD1Lan(double tienGD1Lan) {
        TienGD1Lan = tienGD1Lan;
    }

    public String getSoDuFormat() {
        // Sử dụng String.format với định dạng số có dấu phân cách
        return String.format("%,d", (long) SoDu);
    }

    public String getNumberFormat(double number) {
        // Sử dụng String.format với định dạng số có dấu phân cách
        return String.format("%,d", (long) number);
    }
}
