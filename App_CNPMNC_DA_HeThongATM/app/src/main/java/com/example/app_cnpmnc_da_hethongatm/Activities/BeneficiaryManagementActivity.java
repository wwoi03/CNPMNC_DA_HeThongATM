package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.BeneficiaryManagementAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;

public class BeneficiaryManagementActivity extends AppCompatActivity {

    RecyclerView rc;
    ArrayList<Beneficiary> beneficiaryArrayList;
    BeneficiaryManagementAdapter beneficiaryManagementAdapter;

    ImageView img_Add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist_nguoithuhuong);
        rc = findViewById(R.id.rc_thuhuong);

        // Khởi tạo danh sách beneficiaryArrayList và thêm dữ liệu
        beneficiaryArrayList = new ArrayList<>();
        beneficiaryArrayList.add(new Beneficiary(1, "101987654321", "Tran Phuong Nam", "Sacombank"));
        beneficiaryArrayList.add(new Beneficiary(2, "123456789101", "Nguyen Van A", "Sacombank"));

        // Khởi tạo adapter sau khi có dữ liệu
        beneficiaryManagementAdapter = new BeneficiaryManagementAdapter(this, beneficiaryArrayList);

        rc.setAdapter(beneficiaryManagementAdapter);
        rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Ánh xạ
        img_Add = findViewById(R.id.img_Add);
        //Xử lý sự kiện
        img_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeneficiaryManagementActivity.this, AddBeneficiaryManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
