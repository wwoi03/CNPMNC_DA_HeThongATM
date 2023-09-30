package com.example.app_cnpmnc_da_hethongatm.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class TransferMoney implements Serializable {
    private long HanMucThe;
    private String keySTK;
    private long MaSoThe;
    private String NgayGD;
    private long SoDu;
    private long soTaiKhoan;
    private String TenTK;
    private long TienDaGD;
    private long TienGD1Lan;
    private long TinhTrangTK;

    public  TransferMoney(){

    }

    public TransferMoney(long hanMucThe, String keySTK, long maSoThe, String ngayGD, long soDu, long soTaiKhoan, String tenTK, long tienDaGD, long tienGD1Lan, long tinhTrangTK) {
        HanMucThe = hanMucThe;
        this.keySTK = keySTK;
        MaSoThe = maSoThe;
        NgayGD = ngayGD;
        SoDu = soDu;
        this.soTaiKhoan = soTaiKhoan;
        TenTK = tenTK;
        TienDaGD = tienDaGD;
        TienGD1Lan = tienGD1Lan;
        TinhTrangTK = tinhTrangTK;
    }

    public long getHanMucThe() {
        return HanMucThe;
    }

    public void setHanMucThe(long hanMucThe) {
        HanMucThe = hanMucThe;
    }

    public String getKeySTK() {
        return keySTK;
    }

    public void setKeySTK(String keySTK) {
        this.keySTK = keySTK;
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
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenTK() {
        return TenTK;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
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

    public long getTinhTrangTK() {
        return TinhTrangTK;
    }

    public void setTinhTrangTK(long tinhTrangTK) {
        TinhTrangTK = tinhTrangTK;
    }
}
