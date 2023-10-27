package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class TheNganHang implements Serializable {
    private String Key;
    private long MaSoThe;
    private long SoTaiKhoan;
    private String MaPin;
    private String NgayHetHan;
    private String NgayMoThe;
    private int TinhTrangThe;

    public TheNganHang() {
    }

    public TheNganHang(String key, long maSoThe, long soTaiKhoan, String maPin, String ngayHetHan, String ngayMoThe, int tinhTrangThe) {
        Key = key;
        MaSoThe = maSoThe;
        SoTaiKhoan = soTaiKhoan;
        MaPin = maPin;
        NgayHetHan = ngayHetHan;
        NgayMoThe = ngayMoThe;
        TinhTrangThe = tinhTrangThe;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public long getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(long maSoThe) {
        MaSoThe = maSoThe;
    }

    public long getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public String getMaPin() {
        return MaPin;
    }

    public void setMaPin(String maPin) {
        MaPin = maPin;
    }

    public String getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        NgayHetHan = ngayHetHan;
    }

    public String getNgayMoThe() {
        return NgayMoThe;
    }

    public void setNgayMoThe(String ngayMoThe) {
        NgayMoThe = ngayMoThe;
    }

    public int getTinhTrangThe() {
        return TinhTrangThe;
    }

    public void setTinhTrangThe(int tinhTrangThe) {
        TinhTrangThe = tinhTrangThe;
    }
}

