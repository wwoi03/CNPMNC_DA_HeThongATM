package com.example.app_cnpmnc_da_hethongatm.Model;

import java.io.Serializable;

public class ChucNang implements Serializable {
    private String MaChucNang;
    private String TenChucNang;
    private String IconChucNang;

    public ChucNang() {

    }

    public ChucNang(String MaChucNang, String TenChucNang, String IconChucNang) {
        this.MaChucNang = MaChucNang;
        this.TenChucNang = TenChucNang;
        this.IconChucNang = IconChucNang;
    }

    public String getMaChucNang() {
        return MaChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        MaChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return TenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        TenChucNang = tenChucNang;
    }

    public String getIconChucNang() {
        return IconChucNang;
    }

    public void setIconChucNang(String iconChucNang) {
        IconChucNang = iconChucNang;
    }
}
