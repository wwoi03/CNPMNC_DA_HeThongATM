package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;

public class AddBeneficiaryManagementActivity extends AppCompatActivity {
    Button btn_back, btn_save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taothuhuong);
        InitUI();
        InitListener();

    }

    //Ánh xạ
    public void InitUI(){
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_Save);
    }

    public  void InitListener(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBeneficiaryManagementActivity.this, BeneficiaryManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
