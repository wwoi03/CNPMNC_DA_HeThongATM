package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class TaiKhoanLienKet implements Serializable {
    private long HanMucThe;
    private long MaSoThe;
    private String NgayGD;
    private long SoDu;
    private long SoTaiKhoan;
    private String TenTaiKhoan;
    private long TienDaGD;
    private long TienGD1Lan;
    private int TinhTrangTaiKhoan;

    public TaiKhoanLienKet() {
    }

    public long getHanMucThe() {
        return HanMucThe;
    }

    public void setHanMucThe(long hanMucThe) {
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

    public long getSoDu() {
        return SoDu;
    }

    public void setSoDu(long soDu) {
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

    public long getTienDaGD() {
        return TienDaGD;
    }

    public void setTienDaGD(long tienDaGD) {
        TienDaGD = tienDaGD;
    }

    public long getTienGD1Lan() {
        return TienGD1Lan;
    }

    public void setTienGD1Lan(long tienGD1Lan) {
        TienGD1Lan = tienGD1Lan;
    }

    public int getTinhTrangTaiKhoan() {
        return TinhTrangTaiKhoan;
    }

    public void setTinhTrangTaiKhoan(int tinhTrangTaiKhoan) {
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
    }

    public TaiKhoanLienKet(long hanMucThe, long maSoThe, String ngayGD, long soDu, long soTaiKhoan, String tenTaiKhoan, long tienDaGD, long tienGD1Lan, int tinhTrangTaiKhoan) {
        HanMucThe = hanMucThe;
        MaSoThe = maSoThe;
        NgayGD = ngayGD;
        SoDu = soDu;
        SoTaiKhoan = soTaiKhoan;
        TenTaiKhoan = tenTaiKhoan;
        TienDaGD = tienDaGD;
        TienGD1Lan = tienGD1Lan;
        TinhTrangTaiKhoan = tinhTrangTaiKhoan;
    }
}
