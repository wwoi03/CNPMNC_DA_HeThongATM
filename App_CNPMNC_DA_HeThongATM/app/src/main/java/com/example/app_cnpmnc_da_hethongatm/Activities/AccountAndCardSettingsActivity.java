package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTaiKhoan;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AccountAndCardSettingsActivity extends AppCompatActivity {
    TextView tvAccountNumber,tvTransferLimitCurrent,tv1,tv3,tv_tinhtrang;
    Switch sw_changestatus;
    Intent intent;
    TaiKhoanLienKet taiKhoanLienKet;
    LoaiTaiKhoan loaiTaiKhoan;
    String TenLoaiTk = "";
    ImageView icon_ok,icon_khoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_card_settings);
        InitUI();
        SetText();
        ChangeData();
    }
    private void InitUI(){
        intent = getIntent();
        taiKhoanLienKet = (TaiKhoanLienKet) intent.getSerializableExtra("TaiKhoanNguon");
        tvAccountNumber = findViewById(R.id.tvAccountNumber);
        tvTransferLimitCurrent = findViewById(R.id.tvTransferLimitCurrent);
        tv1 = findViewById(R.id.tv1);
        tv3 = findViewById(R.id.tv3);
        sw_changestatus = findViewById(R.id.sw_changestatus);
        tv_tinhtrang = findViewById(R.id.tv_tinhtrang);
        icon_ok = findViewById(R.id.icon_ok);
        icon_khoa= findViewById(R.id.icon_khoa);

    }
    private void SetText(){
        int color = Color.parseColor("#FF0000");
        tvAccountNumber.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tv1.setText(GetLoaiTaiKhoan(taiKhoanLienKet.getMaLoaiTKKey()));
        tvAccountNumber.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tvTransferLimitCurrent.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tv3.setText(String.valueOf(taiKhoanLienKet.getHanMucTK()));
        if(taiKhoanLienKet.getTinhTrangTaiKhoan() ==0){
            tv_tinhtrang.setText("Đang hoạt động");
            icon_ok.setVisibility(View.VISIBLE);
            icon_khoa.setVisibility(View.INVISIBLE);
            sw_changestatus.setChecked(true);
        }
        else {
            tv_tinhtrang.setText("Đang tạm khóa");
            tv_tinhtrang.setTextColor(color);
            icon_khoa.setVisibility(View.VISIBLE);
            icon_ok.setVisibility(View.INVISIBLE);
            sw_changestatus.setChecked(false);
        }
    }
    private String GetLoaiTaiKhoan(String maloaitk){
        DbHelper.firebaseDatabase.getReference("LoaiTaiKhoan").orderByChild("maloaitk").equalTo(maloaitk)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                loaiTaiKhoan = dataSnapshot.getValue(LoaiTaiKhoan.class);
                                TenLoaiTk = loaiTaiKhoan.getTenLoaiTaiKhoan();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return TenLoaiTk;
    }
    private void ChangeData(){
        sw_changestatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    taiKhoanLienKet.setTinhTrangTaiKhoan(0);
                    UpdateData();
                }
                else {
                    taiKhoanLienKet.setTinhTrangTaiKhoan(1);
                    UpdateData();
                }
            }
        });
    }
    private void UpdateData(){
        Map<String, Object> map = new HashMap<>();
        map.put("TinhTrangTaiKhoan",taiKhoanLienKet.getTinhTrangTaiKhoan());
        DbHelper.firebaseDatabase.getReference("TaiKhoanLienKet").child(taiKhoanLienKet.getKey()).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AccountAndCardSettingsActivity.this,"Doi thanh cong",Toast.LENGTH_SHORT).show();
                        updateText();
                    }
                });
    }
    private void updateText(){
        int color = Color.parseColor("#FF0000");
        int color1 = Color.parseColor("FFFFFFFF");
        DbHelper.firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild(taiKhoanLienKet.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(taiKhoanLienKet.getTinhTrangTaiKhoan() ==0){
                            tv_tinhtrang.setText("Đang hoạt động");
                            tv_tinhtrang.setTextColor(color1);
                            icon_ok.setVisibility(View.VISIBLE);
                            icon_khoa.setVisibility(View.INVISIBLE);
                            sw_changestatus.setChecked(true);
                        }
                        else {
                            tv_tinhtrang.setText("Đang tạm khóa");
                            tv_tinhtrang.setTextColor(color);
                            icon_khoa.setVisibility(View.VISIBLE);
                            icon_ok.setVisibility(View.INVISIBLE);
                            sw_changestatus.setChecked(false);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}