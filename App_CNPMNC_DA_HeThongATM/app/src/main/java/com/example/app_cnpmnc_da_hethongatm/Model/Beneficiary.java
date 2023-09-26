package com.example.app_cnpmnc_da_hethongatm.Model;

public class Beneficiary {
    String TkThuHuong;
    String TenNguoiThuHuong;
    String maSoThe;

    public Beneficiary() {

    }

    public Beneficiary(String tkThuHuong, String tenNguoiThuHuong, String maSoThe) {
        TkThuHuong = tkThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
        this.maSoThe = maSoThe;
    }

    public String getTkThuHuong() {
        return TkThuHuong;
    }

    public void setTkThuHuong(String tkThuHuong) {
        TkThuHuong = tkThuHuong;
    }

    public String getTenNguoiThuHuong() {
        return TenNguoiThuHuong;
    }

    public void setTenNguoiThuHuong(String tenNguoiThuHuong) {
        TenNguoiThuHuong = tenNguoiThuHuong;
    }

    public String getMaSoThe() {
        return maSoThe;
    }

    public void setMaSoThe(String maSoThe) {
        this.maSoThe = maSoThe;
    }
}
