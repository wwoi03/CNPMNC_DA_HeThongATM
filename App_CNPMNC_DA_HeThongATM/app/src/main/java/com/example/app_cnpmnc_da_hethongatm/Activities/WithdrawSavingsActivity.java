package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTaiKhoan;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WithdrawSavingsActivity extends AppCompatActivity {

    TextView tv_taikhoantietkiem, tv_sodutietkiem, tv_ngaygui, tv_kyhan, tv_taikhoanthuhuong;
    Button btn_xulyrutien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruttien);
        initUI();
        initData();
    }

    public void initUI(){
        tv_taikhoantietkiem = findViewById(R.id.tv_taikhoantietkiem);
        tv_sodutietkiem = findViewById(R.id.tv_sodutietkiem);
        tv_ngaygui = findViewById(R.id.tv_ngaygui);
        tv_kyhan = findViewById(R.id.tv_kyhan);
        tv_taikhoanthuhuong = findViewById(R.id.tv_taikhoanthuhuong);
        btn_xulyrutien = findViewById(R.id.btn_xulyruttietkiem);
    }

    public void initData() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String loaiTaiKhoanKey = String.valueOf(dataSnapshot.child("LoaiTaiKhoan").getValue());
                    DatabaseReference loaiTaiKhoanRef = FirebaseDatabase.getInstance().getReference().child("LoaiTaiKhoan").child(loaiTaiKhoanKey);
                    loaiTaiKhoanRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ("Tài khoản tiết kiệm".equals(snapshot.child("TenLoaiTaiKhoan").getValue(String.class))) {
                                DatabaseReference guiTietKiemRef = FirebaseDatabase.getInstance().getReference().child("GuiTietKiem");
                                guiTietKiemRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                            Long taikhoantietkiem = childSnapshot.child("SoTaiKhoan").getValue(long.class);
                                            String tktietkiem = String.valueOf(taikhoantietkiem);
                                            tv_taikhoantietkiem.setText(tktietkiem);

                                            String ngaygui = childSnapshot.child("NgayGui").getValue(String.class);
                                            tv_ngaygui.setText(ngaygui);

                                            Long taikhoannguon = childSnapshot.child("SoTaiKhoanNguon").getValue(long.class);
                                            String tknguon = String.valueOf(taikhoannguon);
                                            tv_taikhoanthuhuong.setText(tknguon);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                Long soDu = dataSnapshot.child("SoDu").getValue(long.class);
                                String soDuTietKiem = String.valueOf(soDu);
                                tv_sodutietkiem.setText(soDuTietKiem);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btn_xulyrutien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference taiKhoanLienKetRef = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");

                taiKhoanLienKetRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String soTk = String.valueOf(dataSnapshot.child("SoTaiKhoan").getValue());
                            String soDuString = String.valueOf(dataSnapshot.child("SoDu").getValue());
                            String loaiTaiKhoankey = String.valueOf(dataSnapshot.child("LoaiTaiKhoan").getValue());
                            DatabaseReference loaiTaiKhoanRef = FirebaseDatabase.getInstance().getReference().child("LoaiTaiKhoan").child(loaiTaiKhoankey);
                            loaiTaiKhoanRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if ("Tài khoản tiết kiệm".equals(snapshot.child("TenLoaiTaiKhoan").getValue(String.class))){
                                        DatabaseReference guiTietKiemRef = FirebaseDatabase.getInstance().getReference().child("GuiTietKiem");
                                        guiTietKiemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                                                    String soTkThuHuong = String.valueOf(childSnapshot.child("SoTaiKhoanNguon").getValue());
                                                    if (soTkThuHuong.equals(soTk)) {

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
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
