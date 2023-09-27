package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class AddBeneficiaryManagementActivity extends AppCompatActivity {
    Button btn_back, btn_save;
    EditText edtName, edtAccountNumber;
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
        edtName = findViewById(R.id.edtName);
        edtAccountNumber = findViewById(R.id.edtAccountNumber);
    }

    //Xử lý sự kiện
    public  void InitListener(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                finish();
            }
        });
    }

    public void insertData(){
        Map<String, Object> map = new HashMap<>();
        map.put("TenNguoiThuHuong", edtName.getText().toString());
        map.put("TKThuHuong", edtAccountNumber.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("ThuHuong").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddBeneficiaryManagementActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddBeneficiaryManagementActivity.this, "Thêm thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
