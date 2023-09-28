package com.example.app_cnpmnc_da_hethongatm.Model;

public class Beneficiary {

    String IdThuHuong;
    String TKThuHuong;
    String TenNguoiThuHuong;
    String MaSoThe;



    public Beneficiary() {

    }

    public Beneficiary(String idThuHuong, String TKThuHuong, String tenNguoiThuHuong, String maSoThe) {
        IdThuHuong = idThuHuong;
        this.TKThuHuong = TKThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
        MaSoThe = maSoThe;
    }

    public String getIdThuHuong() {
        return IdThuHuong;
    }

    public void setIdThuHuong(String idThuHuong) {
        IdThuHuong = idThuHuong;
    }

    public String getTKThuHuong() {
        return TKThuHuong;
    }

    public void setTKThuHuong(String TKThuHuong) {
        this.TKThuHuong = TKThuHuong;
    }

    public String getTenNguoiThuHuong() {
        return TenNguoiThuHuong;
    }

    public void setTenNguoiThuHuong(String tenNguoiThuHuong) {
        TenNguoiThuHuong = tenNguoiThuHuong;
    }

    public String getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(String maSoThe) {
        MaSoThe = maSoThe;
    }
}
