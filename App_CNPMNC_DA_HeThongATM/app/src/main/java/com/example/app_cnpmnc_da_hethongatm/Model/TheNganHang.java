package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class TheNganHang implements Serializable {

    private String LoaiThe;
    private String MaDangNhap;
    private String MaKH; // key trong bảng "KhachHang"
    private String MaPin;
    private String MaSoThe;
    private String NgayHetHan;
    private String NgayMoThe;
    private String SDTDangKy;
    private int TinhTrangThe;

    public TheNganHang() {
    }

    public TheNganHang(String loaiThe, String maDangNhap, String maKH, String maPin, String maSoThe, String ngayHetHan, String ngayMoThe, String SDTDangKy, int tinhTrangThe) {
        LoaiThe = loaiThe;
        MaDangNhap = maDangNhap;
        MaKH = maKH;
        MaPin = maPin;
        MaSoThe = maSoThe;
        NgayHetHan = ngayHetHan;
        NgayMoThe = ngayMoThe;
        this.SDTDangKy = SDTDangKy;
        TinhTrangThe = tinhTrangThe;
    }

    public String getLoaiThe() {
        return LoaiThe;
    }

    public void setLoaiThe(String loaiThe) {
        LoaiThe = loaiThe;
    }

    public String getMaDangNhap() {
        return MaDangNhap;
    }

    public void setMaDangNhap(String maDangNhap) {
        MaDangNhap = maDangNhap;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getMaPin() {
        return MaPin;
    }

    public void setMaPin(String maPin) {
        MaPin = maPin;
    }

    public String getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(String maSoThe) {
        MaSoThe = maSoThe;
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

    public String getSDTDangKy() {
        return SDTDangKy;
    }

    public void setSDTDangKy(String SDTDangKy) {
        this.SDTDangKy = SDTDangKy;
    }

    public int getTinhTrangThe() {
        return TinhTrangThe;
    }

    public void setTinhTrangThe(int tinhTrangThe) {
        TinhTrangThe = tinhTrangThe;
    }
}

