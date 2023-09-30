package com.example.app_cnpmnc_da_hethongatm.Model;

import java.util.HashMap;
import java.util.Map;

public class Card {
    public String LoaiThe;
    public String MaDangNhap;
    public String MaKH;
    public int MaPin;
    public int MaSoThe;
    public String NgayHetHan;
    public String NgayMoThe;
    public String SDTDangKy;
    public int TinhTrangThe;

    public Card() {
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

    public int getMaPin() {
        return MaPin;
    }

    public void setMaPin(int maPin) {
        MaPin = maPin;
    }

    public int getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(int maSoThe) {
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

    public Card(String loaiThe, String maDangNhap, String maKH, int maPin, int maSoThe, String ngayHetHan, String ngayMoThe, String SDTDangKy, int tinhTrangThe) {
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
}

