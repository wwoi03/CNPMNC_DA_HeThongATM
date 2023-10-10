package com.example.app_cnpmnc_da_hethongatm.Model;

public class LoaiTheNganHang {
    private double HanMucLoaiThe;
    private String MaLoaiTNH;
    private String TenTNH;

    public LoaiTheNganHang(){}

    public LoaiTheNganHang(double hanMucLoaiThe, String maLoaiTNH, String tenTNH) {
        HanMucLoaiThe = hanMucLoaiThe;
        MaLoaiTNH = maLoaiTNH;
        TenTNH = tenTNH;
    }

    public double getHanMucLoaiThe() {
        return HanMucLoaiThe;
    }

    public void setHanMucLoaiThe(double hanMucLoaiThe) {
        HanMucLoaiThe = hanMucLoaiThe;
    }

    public String getMaLoaiTNH() {
        return MaLoaiTNH;
    }

    public void setMaLoaiTNH(String maLoaiTNH) {
        MaLoaiTNH = maLoaiTNH;
    }

    public String getTenTNH() {
        return TenTNH;
    }

    public void setTenTNH(String tenTNH) {
        TenTNH = tenTNH;
    }
}
