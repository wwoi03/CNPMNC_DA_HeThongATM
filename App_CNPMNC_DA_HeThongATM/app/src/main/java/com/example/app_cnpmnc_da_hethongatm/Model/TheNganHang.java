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
    private String MaKHKey;

    public TheNganHang() {
    }

    public TheNganHang(String key, long maSoThe, long soTaiKhoan, String maPin, String ngayHetHan, String ngayMoThe, int tinhTrangThe, String maKHKey) {
        Key = key;
        MaSoThe = maSoThe;
        SoTaiKhoan = soTaiKhoan;
        MaPin = maPin;
        NgayHetHan = ngayHetHan;
        NgayMoThe = ngayMoThe;
        TinhTrangThe = tinhTrangThe;
        MaKHKey = maKHKey;
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

    public String getMaKHKey() {
        return MaKHKey;
    }

    public void setMaKHKey(String maKHKey) {
        MaKHKey = maKHKey;
    }

    public String hideCardNumber(long maSoThe) {
        return maskString(String.valueOf(maSoThe), 6, 12, '*');
    }

    public static String maskString(String s, int start, int end, char mask) {
        if (start < 0 || start > s.length() || end < 0 || end > s.length() || start > end) {
            return s;
        }

        StringBuilder masked = new StringBuilder(s);
        for (int i = start; i < end; i++) {
            masked.setCharAt(i, mask);
        }

        return formatString(masked.toString());
    }

    public static String formatString(String s) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(' ');
            }
            formatted.append(s.charAt(i));
        }

        return formatted.toString();
    }
}

