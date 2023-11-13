package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private String Key;
    private String CCCD;
    private String TenKH;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String Email;
    private String SoDienThoai;
    private String NgayTao;
    private String MatKhau;
    private String MaNhanVienKey;

    public KhachHang() {
    }

    public KhachHang(String key, String CCCD, String tenKH, String ngaySinh, String gioiTinh, String diaChi, String email, String soDienThoai, String ngayTao, String matKhau, String maNhanVienKey) {
        Key = key;
        this.CCCD = CCCD;
        TenKH = tenKH;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        Email = email;
        SoDienThoai = soDienThoai;
        NgayTao = ngayTao;
        MatKhau = matKhau;
        MaNhanVienKey = maNhanVienKey;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
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

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getMaNhanVienKey() {
        return MaNhanVienKey;
    }

    public void setMaNhanVienKey(String maNhanVienKey) {
        MaNhanVienKey = maNhanVienKey;
    }
}
