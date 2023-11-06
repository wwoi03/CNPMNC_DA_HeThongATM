package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class HanMucChuyenTien implements Serializable {
    private String Key;
    private double HanMuc;
    private String NoiDung;

    public HanMucChuyenTien() {
    }

    public HanMucChuyenTien(String key, double hanMuc, String noiDung) {
        Key = key;
        HanMuc = hanMuc;
        NoiDung = noiDung;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public double getHanMuc() {
        return HanMuc;
    }

    public void setHanMuc(double hanMuc) {
        HanMuc = hanMuc;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNumberFormat(double number) {
        // Sử dụng String.format với định dạng số có dấu phân cách
        return String.format("%,d", (long) number);
    }
}
