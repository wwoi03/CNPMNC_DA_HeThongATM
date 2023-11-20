package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
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

    Toolbar tbToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_card_settings);


        InitUI();
        UtilityClass.setupToolbar(this, tbToolbar, "Rút tiền tiết kiệm");
        SetText();
        ChangeData();
    }
    private void InitUI(){
        intent = getIntent();
        taiKhoanLienKet = (TaiKhoanLienKet) intent.getSerializableExtra("TaiKhoanNguon");
        tvAccountNumber = findViewById(R.id.tvAccountNumber);
        tvTransferLimitCurrent = findViewById(R.id.tvTransferLimitCurrent);
        tv3 = findViewById(R.id.tv3);
        sw_changestatus = findViewById(R.id.sw_changestatus);
        tv_tinhtrang = findViewById(R.id.tv_tinhtrang);
        icon_ok = findViewById(R.id.icon_ok);
        icon_khoa= findViewById(R.id.icon_khoa);
        tbToolbar= findViewById(R.id.tbToolbar);
    }
    private void SetText(){
        tvAccountNumber.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tvAccountNumber.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tvTransferLimitCurrent.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
        tv3.setText(taiKhoanLienKet.getNumberFormat(taiKhoanLienKet.getHanMucTK()) + " VND");
        if(taiKhoanLienKet.getTinhTrangTaiKhoan() ==0){
            tv_tinhtrang.setText("Đang hoạt động");
            icon_ok.setVisibility(View.VISIBLE);
            icon_khoa.setVisibility(View.INVISIBLE);
            sw_changestatus.setChecked(true);
        }
        else {
            tv_tinhtrang.setText("Đang tạm khóa");
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

        DbHelper.firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild(taiKhoanLienKet.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(taiKhoanLienKet.getTinhTrangTaiKhoan() ==0){
                            tv_tinhtrang.setText("Đang hoạt động");
                            icon_ok.setVisibility(View.VISIBLE);
                            icon_khoa.setVisibility(View.INVISIBLE);
                            sw_changestatus.setChecked(true);
                        }
                        else {
                            tv_tinhtrang.setText("Đang tạm khóa");
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

    // xử lý sự kiện ấn nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Kết thúc Activity hiện tại và quay lại  Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}