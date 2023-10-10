package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class TaiKhoanLienKet implements Serializable {
    private double HanMucThe;
    private long MaSoThe;
    private String NgayGD;
    private double SoDu;
    private long SoTaiKhoan;
    private String TenTaiKhoan;
    private double TienDaGD;
    private double TienGD1Lan;
    private int TinhTrangTaiKhoan;
    private String LoaiTaiKhoan;

    public TaiKhoanLienKet() {
    }

    public TaiKhoanLienKet(double hanMucThe, long maSoThe, String ngayGD, double soDu, long soTaiKhoan, String tenTaiKhoan, double tienDaGD, double tienGD1Lan, int tinhTrangTaiKhoan, String loaiTaiKhoan) {
        HanMucThe = hanMucThe;
        MaSoThe = maSoThe;
        NgayGD = ngayGD;
        SoDu = soDu;
        SoTaiKhoan = soTaiKhoan;
        TenTaiKhoan = tenTaiKhoan;
        TienDaGD = tienDaGD;
        TienGD1Lan = tienGD1Lan;
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
        LoaiTaiKhoan = loaiTaiKhoan;
    }

    public double getHanMucThe() {
        return HanMucThe;
    }

    public void setHanMucThe(double hanMucThe) {
        HanMucThe = hanMucThe;
    }

    public long getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(long maSoThe) {
        MaSoThe = maSoThe;
    }

    public String getNgayGD() {
        return NgayGD;
    }

    public void setNgayGD(String ngayGD) {
        NgayGD = ngayGD;
    }

    public double getSoDu() {
        return SoDu;
    }

    public void setSoDu(double soDu) {
        SoDu = soDu;
    }

    public long getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
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

    public int getTinhTrangTaiKhoan() {
        return TinhTrangTaiKhoan;
    }

    public void setTinhTrangTaiKhoan(int tinhTrangTaiKhoan) {
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
    }

    public String getLoaiTaiKhoan() {
        return LoaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        LoaiTaiKhoan = loaiTaiKhoan;
    }
}
