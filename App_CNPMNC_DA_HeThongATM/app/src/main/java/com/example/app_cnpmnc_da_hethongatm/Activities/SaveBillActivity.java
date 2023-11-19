package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.MauChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;

public class SaveBillActivity extends AppCompatActivity {
    TextView rs_tien,rs_nguoinhan,rs_stknguon,rs_stkgui,rs_nguoigui,rs_thoigian,rs_magd,rs_noidung;
    Button btn_backhome;
    CardView cv_luumau;
    Intent intent;
    Config config;
    int i = 0;
    String NgayGui,GioGui,NoiDung,MaGd,TienGD;
    TaiKhoanLienKet taiKhoanNguoiNhan = new TaiKhoanLienKet();
    TaiKhoanLienKet taiKhoanNguoiGui= new TaiKhoanLienKet();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_chuyentien);
        InitUI();
        GetIntent();
        SetText();
        config = new Config(this);

        btn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SaveBillActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        cv_luumau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i ==0){
                    MauChuyenTien mauChuyenTien = new MauChuyenTien(taiKhoanNguoiNhan.getTenTK(),taiKhoanNguoiNhan.getSoTaiKhoan(),Double.parseDouble(TienGD),NoiDung,config.getCustomerKey());
                    DbHelper.SaveBill(mauChuyenTien);
                    DbHelper.BuilderXinXo(SaveBillActivity.this,"Lưu thành công");
                    i++;
                }
            }
        });
    }
    private void InitUI(){
        rs_tien=findViewById(R.id.rs_tien);
        rs_nguoinhan=findViewById(R.id.rs_nguoinhan);
        rs_stknguon=findViewById(R.id.rs_stknguon);
        rs_stkgui=findViewById(R.id.rs_stkgui);
        rs_nguoigui=findViewById(R.id.rs_nguoigui);
        rs_thoigian=findViewById(R.id.rs_thoigian);
        rs_magd=findViewById(R.id.rs_magd);
        rs_noidung=findViewById(R.id.rs_noidung);
        btn_backhome=findViewById(R.id.btn_backhome);
        cv_luumau=findViewById(R.id.cv_luumau);
    }
    private void GetIntent(){
        intent = getIntent();
        taiKhoanNguoiNhan =(TaiKhoanLienKet) intent.getSerializableExtra("NguoiNhan");
        taiKhoanNguoiGui =(TaiKhoanLienKet) intent.getSerializableExtra("NguoiGui");
        NgayGui = intent.getStringExtra("NgayGui");
        GioGui = intent.getStringExtra("GioGui");
        NoiDung = intent.getStringExtra("NoiDung");
        MaGd = intent.getStringExtra("MaGd");
        TienGD =intent.getStringExtra("TienGD");

    }
    private void SetText(){
        double number = Double.parseDouble(TienGD);
        long tiengd = (long) number;
        rs_tien.setText(String.valueOf(tiengd));
        rs_nguoigui.setText(taiKhoanNguoiGui.getTenTK());
        rs_nguoinhan.setText(taiKhoanNguoiNhan.getTenTK());
        rs_stkgui.setText(String.valueOf(taiKhoanNguoiGui.getSoTaiKhoan()));
        rs_stknguon.setText(String.valueOf(taiKhoanNguoiNhan.getSoTaiKhoan()));
        rs_thoigian.setText(NgayGui +" "+GioGui);
        rs_noidung.setText(NoiDung);
        rs_magd.setText(MaGd);
    }
}
