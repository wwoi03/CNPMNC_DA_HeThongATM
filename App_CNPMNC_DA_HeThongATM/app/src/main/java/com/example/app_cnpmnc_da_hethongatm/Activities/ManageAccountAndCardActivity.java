package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ManageAccountAndCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import me.relex.circleindicator.CircleIndicator3;

public class ManageAccountAndCardActivity extends AppCompatActivity implements ManageAccountAndCardAdapter.Listener {
    // View
    ViewPager2 vp2AccountsAndCards;
    CircleIndicator3 ci3;

    // Adapter
    ManageAccountAndCardAdapter manageAccountAndCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account_and_card);

        initUI();
        initData();
        initListener();
    }

    // Ánh xạ view
    public void initUI() {
        vp2AccountsAndCards = findViewById(R.id.vp2AccountsAndCards);
        ci3 = findViewById(R.id.ci3);
    }

    // Khởi tạo dữ liệu
    public void initData() {
        FirebaseRecyclerOptions<TaiKhoanLienKet> danhSachTaiKhoanLienKet = DbHelper.getAffiliateAccounts();
        manageAccountAndCardAdapter = new ManageAccountAndCardAdapter(danhSachTaiKhoanLienKet, this);
        vp2AccountsAndCards.setAdapter(manageAccountAndCardAdapter);
        ci3.setViewPager(vp2AccountsAndCards);
    }

    // Xử lý sự kiện
    public void initListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        manageAccountAndCardAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        manageAccountAndCardAdapter.stopListening();
    }

    // Xử lý sự kiện khi bấm vào icon hiển thị số dư
    @Override
    public void setOnClickIconSurplusListener(ImageView iconSurplus, TextView tvSurplus, TaiKhoanLienKet taiKhoanLienKet) {
        // kiểm tra số dư đang hiển thị kiểu mã hóa?
        if (String.valueOf(tvSurplus.getText()).contains("*")) { // đang mã hóa
            tvSurplus.setText(taiKhoanLienKet.getSoDuFormat());
            iconSurplus.setImageResource(R.drawable.eye_slash_solid);
        } else { // không mã hóa
            tvSurplus.setText("*******đ");
            iconSurplus.setImageResource(R.drawable.baseline_remove_red_eye_24);
        }
    }
}