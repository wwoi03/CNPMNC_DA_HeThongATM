package com.example.app_cnpmnc_da_hethongatm.Model;

public class AnhCCCD {
    private String CCCD;
    private String MatTruoc;
    private String MatSau;

    public AnhCCCD() {
    }

    public AnhCCCD(String CCCD, String matTruoc, String matSau) {
        this.CCCD = CCCD;
        MatTruoc = matTruoc;
        MatSau = matSau;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getMatTruoc() {
        return MatTruoc;
    }

    public void setMatTruoc(String matTruoc) {
        MatTruoc = matTruoc;
    }

    public String getMatSau() {
        return MatSau;
    }

    public void setMatSau(String matSau) {
        MatSau = matSau;
    }
}
