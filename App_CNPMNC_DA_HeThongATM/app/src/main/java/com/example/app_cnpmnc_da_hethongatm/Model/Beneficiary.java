package com.example.app_cnpmnc_da_hethongatm.Model;

public class Beneficiary {
    int Id;
    String soThe;
    String name;

    String bankName;

    public Beneficiary(int id, String soThe, String name, String bankName) {
        Id = id;
        this.soThe = soThe;
        this.name = name;
        this.bankName = bankName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
