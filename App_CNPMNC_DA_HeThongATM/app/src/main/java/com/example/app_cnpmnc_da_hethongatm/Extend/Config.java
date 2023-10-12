package com.example.app_cnpmnc_da_hethongatm.Extend;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

    String fileName = "config";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Config(Context context) {
        sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public String getCustomerPhone() {
        return sharedPreferences.getString("customerPhone", "");
    }
    public String getCustomerKey() {
        return sharedPreferences.getString("customerKey", "");
    }
    public String getCustomerName() {
        return sharedPreferences.getString("customerName", "");
    }
}
