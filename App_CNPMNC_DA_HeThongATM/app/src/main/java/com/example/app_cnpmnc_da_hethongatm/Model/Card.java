package com.example.app_cnpmnc_da_hethongatm.Model;

import java.util.HashMap;
import java.util.Map;

public class Card {

    public String loaithe;
   public String madangnhap;
   public String makh;
   public int mapin;
   public int masothe;
   public String ngayhethan;
   public String ngaymothe;
   public String sdtdangky;
   public int tinhtrangthe;


   public Card(){}
    public Card(String loaithe, String madangnhap, String makh, int mapin, int masothe, String ngayhethan, String ngaymothe, String sdtdangky, int tinhtrangthe) {
        this.loaithe = loaithe;
        this.madangnhap = madangnhap;
        this.makh = makh;
        this.mapin = mapin;
        this.masothe = masothe;
        this.ngayhethan = ngayhethan;
        this.ngaymothe = ngaymothe;
        this.sdtdangky = sdtdangky;
        this.tinhtrangthe = tinhtrangthe;
    }

    public String getLoaithe() {
        return loaithe;
    }

    public void setLoaithe(String loaithe) {
        this.loaithe = loaithe;
    }

    public String getMadangnhap() {
        return madangnhap;
    }

    public void setMadangnhap(String madangnhap) {
        this.madangnhap = madangnhap;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public int getMapin() {
        return mapin;
    }

    public void setMapin(int mapin) {
        this.mapin = mapin;
    }

    public int getMasothe() {
        return masothe;
    }

    public void setMasothe(int masothe) {
        this.masothe = masothe;
    }

    public String getNgayhethan() {
        return ngayhethan;
    }

    public void setNgayhethan(String ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    public String getNgaymothe() {
        return ngaymothe;
    }

    public void setNgaymothe(String ngaymothe) {
        this.ngaymothe = ngaymothe;
    }

    public String getSdtdangky() {
        return sdtdangky;
    }

    public void setSdtdangky(String sdtdangky) {
        this.sdtdangky = sdtdangky;
    }

    public int getTinhtrangthe() {
        return tinhtrangthe;
    }

    public void setTinhtrangthe(int tinhtrangthe) {
        this.tinhtrangthe = tinhtrangthe;
    }

    public Map<String,Object>toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("tinhtrangthe",tinhtrangthe);
        return result;
    }
}

