package com.example.app_cnpmnc_da_hethongatm.Model;

public class Beneficiary {

    int IdThuHuong;
    String TKThuHuong;
    String TenNguoiThuHuong;
    long MaSoThe;



    public Beneficiary() {

    }

    public Beneficiary(int idThuHuong, String TKThuHuong, String tenNguoiThuHuong, long maSoThe) {
        IdThuHuong = idThuHuong;
        this.TKThuHuong = TKThuHuong;
        TenNguoiThuHuong = tenNguoiThuHuong;
        MaSoThe = maSoThe;
    }

    public int getIdThuHuong() {
        return IdThuHuong;
    }

    public void setIdThuHuong(int idThuHuong) {
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

    public long getMaSoThe() {
        return MaSoThe;
    }

    public void setMaSoThe(long maSoThe) {
        MaSoThe = maSoThe;
    }
}
