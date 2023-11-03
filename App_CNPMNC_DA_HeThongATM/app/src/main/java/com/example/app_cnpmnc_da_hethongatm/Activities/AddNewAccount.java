package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddNewAccount extends AppCompatActivity {
    Button  btTransferMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account);
        initUI();
        initData();
        initListener();

    }
    public void initUI() {
        btTransferMoney = findViewById(R.id.btTransferMoney);
    }
    private void initData() {
        btTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAccount.this, AgreeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initListener() {
    }





}




