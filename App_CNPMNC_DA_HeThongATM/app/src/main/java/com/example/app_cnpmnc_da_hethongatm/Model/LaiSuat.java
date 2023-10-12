package com.example.app_cnpmnc_da_hethongatm.Model;

public class LaiSuat {

    private  String KyHan;
    private String MaLaiSuat;
    private String Tile;

    public String getKyHan() {
        return KyHan;
    }

    public void setKyHan(String kyHan) {
        KyHan = kyHan;
    }

    public String getMaLaiSuat() {
        return MaLaiSuat;
    }

    public void setMaLaiSuat(String maLaiSuat) {
        MaLaiSuat = maLaiSuat;
    }

    public String getTile() {
        return Tile;
    }

    public void setTile(String tile) {
        Tile = tile;
    }

    public LaiSuat(String kyHan, String maLaiSuat, String tile) {
        KyHan = kyHan;
        MaLaiSuat = maLaiSuat;
        Tile = tile;
    }
}
