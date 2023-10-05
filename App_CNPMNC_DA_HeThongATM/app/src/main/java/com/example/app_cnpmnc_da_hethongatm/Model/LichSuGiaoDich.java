package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class LichSuGiaoDich implements Serializable {
    private String GioGiaoDich;
    private String NgayGiaoDich;
    private String NoiDungChuyenKhoan;
    private long SoTaiKhoan;
    private double SoTienGiaoDich;
    private long TaiKhoanNhan;
    private double SoDuHienTai;

    public LichSuGiaoDich(){

    }

    public LichSuGiaoDich(String gioGiaoDich, String ngayGiaoDich, String noiDungChuyenKhoan, long soTaiKhoan, double soTienGiaoDich, long taiKhoanNhan, double soDuHienTai) {
        GioGiaoDich = gioGiaoDich;
        NgayGiaoDich = ngayGiaoDich;
        NoiDungChuyenKhoan = noiDungChuyenKhoan;
        SoTaiKhoan = soTaiKhoan;
        SoTienGiaoDich = soTienGiaoDich;
        TaiKhoanNhan = taiKhoanNhan;
        SoDuHienTai = soDuHienTai;
    }

    public String getGioGiaoDich() {
        return GioGiaoDich;
    }

    public void setGioGiaoDich(String gioGiaoDich) {
        GioGiaoDich = gioGiaoDich;
    }

    public String getNgayGiaoDich() {
        return NgayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        NgayGiaoDich = ngayGiaoDich;
    }

    public String getNoiDungChuyenKhoan() {
        return NoiDungChuyenKhoan;
    }

    public void setNoiDungChuyenKhoan(String noiDungChuyenKhoan) {
        NoiDungChuyenKhoan = noiDungChuyenKhoan;
    }

    public long getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(long soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public double getSoTienGiaoDich() {
        return SoTienGiaoDich;
    }

    public void setSoTienGiaoDich(double soTienGiaoDich) {
        SoTienGiaoDich = soTienGiaoDich;
    }

    public long getTaiKhoanNhan() {
        return TaiKhoanNhan;
    }

    public void setTaiKhoanNhan(long taiKhoanNhan) {
        TaiKhoanNhan = taiKhoanNhan;
    }

    public double getSoDuHienTai() {
        return SoDuHienTai;
    }

    public void setSoDuHienTai(double soDuHienTai) {
        SoDuHienTai = soDuHienTai;
    }
}
