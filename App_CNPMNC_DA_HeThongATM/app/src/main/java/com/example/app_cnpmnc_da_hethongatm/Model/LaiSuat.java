package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class LaiSuat implements Serializable {
    private String Key;
    private String KyHan;
    private double TiLe;

    public LaiSuat() {

    }

    public LaiSuat(String key, String kyHan, double tiLe) {
        Key = key;
        KyHan = kyHan;
        TiLe = tiLe;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
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
