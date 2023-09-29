package com.example.app_cnpmnc_da_hethongatm.Model;

public class TransferMoney {
    private long HanMucThe;
    private long MaSoThe;
    private String NgayGD;
    private long SoDu;
    private long SoTaiKhoan;
    private String TenTK;
    private long TienDaGD;
    private long TienGD1Lan;
    private long TinhTrangTK;

    public  TransferMoney(){

    }

    public TransferMoney(long hanMucThe, long maSoThe, String ngayGD, long soDu, long soTaiKhoan, String tenTK, long tienDaGD, long tienGD1Lan, long tinhTrangTK) {
        HanMucThe = hanMucThe;
        MaSoThe = maSoThe;
        NgayGD = ngayGD;
        SoDu = soDu;
        SoTaiKhoan = soTaiKhoan;
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

    @Override
    public String toString() {
        return "TransferMoney{" +
                "HanMucThe=" + HanMucThe +
                ", MaSoThe=" + MaSoThe +
                ", NgayGD='" + NgayGD + '\'' +
                ", SoDu=" + SoDu +
                ", SoTaiKhoan=" + SoTaiKhoan +
                ", TenTK='" + TenTK + '\'' +
                ", TienDaGD=" + TienDaGD +
                ", TienGD1Lan=" + TienGD1Lan +
                ", TinhTrangTK=" + TinhTrangTK +
                '}';
    }
}
