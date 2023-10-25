package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class GiaoDich implements Serializable {
    private String Key;
    private long TaiKhoanNguon;
    private long TaiKhoanNhan;
    private String NgayGiaoDich;
    private String GioGiaoDich;
    private String NoiDungChuyenKhoan;
    private double SoTienGiaoDich;
    private double PhiGiaoDich;
    private double SoDuLucGui;
    private double SoDuLucNhan;
    private String LoaiGiaoDichKey;

    public GiaoDich(String key, long taiKhoanNguon, long taiKhoanNhan, String ngayGiaoDich, String gioGiaoDich,
                    String noiDungChuyenKhoan, double soTienGiaoDich, double phiGiaoDich, double soDuLucGui,
                    double soDuLucNhan, String loaiGiaoDichKey) {
        Key = key;
        TaiKhoanNguon = taiKhoanNguon;
        TaiKhoanNhan = taiKhoanNhan;
        NgayGiaoDich = ngayGiaoDich;
        GioGiaoDich = gioGiaoDich;
        NoiDungChuyenKhoan = noiDungChuyenKhoan;
        SoTienGiaoDich = soTienGiaoDich;
        PhiGiaoDich = phiGiaoDich;
        SoDuLucGui = soDuLucGui;
        SoDuLucNhan = soDuLucNhan;
        LoaiGiaoDichKey = loaiGiaoDichKey;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public long getTaiKhoanNguon() {
        return TaiKhoanNguon;
    }

    public void setTaiKhoanNguon(long taiKhoanNguon) {
        TaiKhoanNguon = taiKhoanNguon;
    }

    public long getTaiKhoanNhan() {
        return TaiKhoanNhan;
    }

    public void setTaiKhoanNhan(long taiKhoanNhan) {
        TaiKhoanNhan = taiKhoanNhan;
    }

    public String getNgayGiaoDich() {
        return NgayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        NgayGiaoDich = ngayGiaoDich;
    }

    public String getGioGiaoDich() {
        return GioGiaoDich;
    }

    public void setGioGiaoDich(String gioGiaoDich) {
        GioGiaoDich = gioGiaoDich;
    }

    public String getNoiDungChuyenKhoan() {
        return NoiDungChuyenKhoan;
    }

    public void setNoiDungChuyenKhoan(String noiDungChuyenKhoan) {
        NoiDungChuyenKhoan = noiDungChuyenKhoan;
    }

    public double getSoTienGiaoDich() {
        return SoTienGiaoDich;
    }

    public void setSoTienGiaoDich(double soTienGiaoDich) {
        SoTienGiaoDich = soTienGiaoDich;
    }

    public double getPhiGiaoDich() {
        return PhiGiaoDich;
    }

    public void setPhiGiaoDich(double phiGiaoDich) {
        PhiGiaoDich = phiGiaoDich;
    }

    public double getSoDuLucGui() {
        return SoDuLucGui;
    }

    public void setSoDuLucGui(double soDuLucGui) {
        SoDuLucGui = soDuLucGui;
    }

    public double getSoDuLucNhan() {
        return SoDuLucNhan;
    }

    public void setSoDuLucNhan(double soDuLucNhan) {
        SoDuLucNhan = soDuLucNhan;
    }

    public String getLoaiGiaoDichKey() {
        return LoaiGiaoDichKey;
    }

    public void setLoaiGiaoDichKey(String loaiGiaoDichKey) {
        LoaiGiaoDichKey = loaiGiaoDichKey;
    }
}
