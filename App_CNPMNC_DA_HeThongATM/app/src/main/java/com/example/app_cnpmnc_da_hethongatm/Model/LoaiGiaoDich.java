package com.example.app_cnpmnc_da_hethongatm.Model;

public class LoaiGiaoDich {
    private String Key;
    private String TenLoai;

    public LoaiGiaoDich() {
    }

    public LoaiGiaoDich(String key, String tenLoai) {
        Key = key;
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
}
