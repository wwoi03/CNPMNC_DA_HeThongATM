package com.example.app_cnpmnc_da_hethongatm.Extend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Config {
    String fileName = "config";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Config(Context context) {
        sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public Boolean getLinkConfig() {
        File externalDir = Environment.getExternalStorageDirectory(); // Lấy thư mục lưu trữ bên ngoài.
        // Tạo một đối tượng File cho tệp cấu hình.
        File configFile = new File(externalDir, fileName);
        // Lấy đường dẫn tuyệt đối của tệp cấu hình.
        if(configFile.exists()){
            return true;
        }
        return false;
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
    public Boolean getStateLogin(){
        return sharedPreferences.getBoolean("stateLogin",false);
    }
    public void ClearData(){
        editor.putString("customerKey","");
        editor.putString("customerPhone","");
        editor.putString("customerName","");
        editor.putBoolean("stateLogin",false);
        editor.apply();
    }
}
