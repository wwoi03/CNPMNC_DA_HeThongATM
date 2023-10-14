package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class WithdrawSavingsActivity extends AppCompatActivity {

    TextView et_ruttientietkiem, et_congtientietkiem;
    Button btn_xulyrutien;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruttien);
        initUI();
        initData();
    }

    public void initUI(){
        et_congtientietkiem = findViewById(R.id.et_congtientietkiem);
        et_ruttientietkiem = findViewById(R.id.et_ruttientietkiem);
        btn_xulyrutien = findViewById(R.id.btn_xulyruttietkiem);
    }

    public void initData() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String loaiTaiKhoanKey = String.valueOf(dataSnapshot.child("LoaiTaiKhoan").getValue());

                    if (loaiTaiKhoanKey.equals("alfkhwriskjvb")) {
                        DatabaseReference loaiTaiKhoanRef = FirebaseDatabase.getInstance().getReference().child("LoaiTaiKhoan").child(loaiTaiKhoanKey);
                        loaiTaiKhoanRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                double sodu = dataSnapshot.child("SoDu").getValue(Long.class).doubleValue();
                                String soDU = String.valueOf(sodu);
                                et_ruttientietkiem.setText(soDU);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else if (dataSnapshot.getKey().equals("-NgchXtP1Msn2nPXDFHr")){
                        double sodu = dataSnapshot.child("SoDu").getValue(Long.class).doubleValue();
                        String soDU = String.valueOf(sodu);
                        et_congtientietkiem.setText(soDU);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_xulyrutien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double soDuAlfkhwriskjvb = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String loaiTaiKhoanKey = String.valueOf(dataSnapshot.child("LoaiTaiKhoan").getValue());
                            if (loaiTaiKhoanKey.equals("alfkhwriskjvb")) {
                                // Lưu số dư hiện tại của tài khoản này
                                soDuAlfkhwriskjvb = dataSnapshot.child("SoDu").getValue(Double.class);
                                // Cập nhật số dư của tài khoản này thành 0
                                dataSnapshot.getRef().child("SoDu").setValue(0);
                            }
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if(dataSnapshot.getKey().equals("-NgchXtP1Msn2nPXDFHr")){
                                    Double soDuNgchXtP1Msn2nPXDFHr = dataSnapshot.child("SoDu").getValue(Double.class);
                                    dataSnapshot.getRef().child("SoDu").setValue(soDuNgchXtP1Msn2nPXDFHr + soDuAlfkhwriskjvb);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
}
    }
