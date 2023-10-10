package com.example.app_cnpmnc_da_hethongatm.Model;

public class LaiSuat {
    private String MaLaiSuat;
    private String KyHan;
    private double TiLe;

    public LaiSuat() {

    }

    public LaiSuat(String maLaiSuat, String kyHan, double tiLe) {
        MaLaiSuat = maLaiSuat;
        KyHan = kyHan;
        TiLe = tiLe;
    }

    public String getMaLaiSuat() {
        return MaLaiSuat;
    }

    public void setMaLaiSuat(String maLaiSuat) {
        MaLaiSuat = maLaiSuat;
    }

    public String getKyHan() {
        return KyHan;
    }

    public void setKyHan(String kyHan) {
        KyHan = kyHan;
    }

    public double getTiLe() {
        return TiLe;
    }

    public void setTiLe(double tiLe) {
        TiLe = tiLe;
    }
}
