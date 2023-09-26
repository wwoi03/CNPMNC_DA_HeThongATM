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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BeneficiaryManagementActivity extends AppCompatActivity {

    RecyclerView rc;
    BeneficiaryManagementAdapter beneficiaryManagementAdapter;

    ImageView img_Add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist_nguoithuhuong);
        rc = findViewById(R.id.rc_thuhuong);


        rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FirebaseRecyclerOptions<Beneficiary> options =
                new FirebaseRecyclerOptions.Builder<Beneficiary>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ThuHuong"), Beneficiary.class)
                        .build();

        beneficiaryManagementAdapter = new BeneficiaryManagementAdapter(options);
        rc.setAdapter(beneficiaryManagementAdapter);
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

    @Override
    protected void onStart() {
        super.onStart();
        beneficiaryManagementAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beneficiaryManagementAdapter.stopListening();
    }
}
