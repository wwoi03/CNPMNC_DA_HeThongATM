package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddNewAccount extends AppCompatActivity {
    Button  btTransferMoney;
    TextView tvSourceAccount;
    TextInputEditText etConten, etMon;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account);
        initUI();
        initData();
        initListener();

    }
    public void initUI() {

        config = new Config(AddNewAccount.this);
        btTransferMoney = findViewById(R.id.btTransferMoney);
        tvSourceAccount =findViewById(R.id.tvSourceAccount);
        etMon = findViewById(R.id.etMon);
        etConten = findViewById(R.id.etConten);

        Log.d("TAG", String.valueOf(config.getCustomerKey()));

    }
    private void initData() {
        Intent intent= getIntent();
        Log.d("TAG", String.valueOf(DbHelper.getAcountIDbyCusKey(config.getCustomerKey())));
        tvSourceAccount.setText(String.valueOf(DbHelper.getAcountIDbyCusKey(config.getCustomerKey())));
        if(intent!=null&&intent.hasExtra("TaiKhoanSoDep")){
            etMon.setText(intent.getStringExtra("TaiKhoanSoDep"));
        }
        etConten.setText(config.getCustomerName());
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




