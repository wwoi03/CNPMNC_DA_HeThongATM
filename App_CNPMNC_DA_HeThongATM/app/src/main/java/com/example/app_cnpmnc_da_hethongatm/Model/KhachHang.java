package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private String CCCD;
    private String DiaChi;
    private String Email;
    private String GioiTinh;
    private String NgayTao;
    private String SoDienThoai;
    private String MatKhau;
    private String TenKhachHang;

    public KhachHang() {
    }

    public KhachHang(String CCCD, String diaChi, String email, String gioiTinh, String ngayTao, String soDienThoai, String matKhau, String tenKhachHang) {
        this.CCCD = CCCD;
        DiaChi = diaChi;
        Email = email;
        GioiTinh = gioiTinh;
        NgayTao = ngayTao;
        SoDienThoai = soDienThoai;
        MatKhau = matKhau;
        TenKhachHang = tenKhachHang;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }
}
