package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewaccountActivity extends AppCompatActivity {
    private EditText tentaikhoan,nhapsotaikhoan;
    private Button btnlogin, btnregister;
    private FirebaseAuth mAuth;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
        mAuth = FirebaseAuth.getInstance();

        tentaikhoan = findViewById(R.id.tentaikhoan);
        nhapsotaikhoan = findViewById(R.id.nhapsotaikhoan);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void register () {
        Intent i = new Intent(com.example.app_cnpmnc_da_hethongatm.Activities.NewaccountActivity.this, RegisterActivity.class);
        startActivity(i);

    }

    private void login() {
        String tentk, stk;
        tentk = tentaikhoan.getText().toString();
        stk = tentaikhoan.getText().toString();

        if (TextUtils.isEmpty(tentk)) {
            Toast.makeText(this, "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(stk)) {
            Toast.makeText(this, "Vui lòng nhập stk mới ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(tentk,stk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(com.example.app_cnpmnc_da_hethongatm.Activities.NewaccountActivity.this, "Tạo tài khoản mới thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(com.example.app_cnpmnc_da_hethongatm.Activities.NewaccountActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(com.example.app_cnpmnc_da_hethongatm.Activities.NewaccountActivity.this, "Số tài khoản này đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}