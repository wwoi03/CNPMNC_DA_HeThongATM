package com.example.app_cnpmnc_da_hethongatm.Model;

public class LoaiGiaoDich {
    private String Key;
    private String MaLoaiGD;
    private String TenLoai;

    public LoaiGiaoDich() {
    }

    public LoaiGiaoDich(String key, String maLoaiGD, String tenLoai) {
        Key = key;
        MaLoaiGD = maLoaiGD;
        TenLoai = tenLoai;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getMaLoaiGD() {
        return MaLoaiGD;
    }

    public void setMaLoaiGD(String maLoaGD) {
        MaLoaiGD = maLoaGD;
    }
}
