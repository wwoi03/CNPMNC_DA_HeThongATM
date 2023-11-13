package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ManageAccountAndCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTaiKhoan;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import me.relex.circleindicator.CircleIndicator3;

public class ManageAccountAndCardActivity extends AppCompatActivity implements ManageAccountAndCardAdapter.Listener {
    // View
    ViewPager2 vp2AccountsAndCards;
    CircleIndicator3 ci3;
    LinearLayout llSettingCard, llHistoryTransferMoney, llStatement, llSaving, llWithdrawSaving, llHistoryInterestRate;
    Toolbar tbToolbar;

    // Adapter
    ManageAccountAndCardAdapter manageAccountAndCardAdapter;

    //
    TaiKhoanLienKet currentAccountCardInPage;
    String taiKhoanKey;

    Config config;

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
        tbToolbar = findViewById(R.id.tbToolbar);
        vp2AccountsAndCards = findViewById(R.id.vp2AccountsAndCards);
        ci3 = findViewById(R.id.ci3);
        llSettingCard = findViewById(R.id.llSettingCard);
        llHistoryTransferMoney = findViewById(R.id.llHistoryTransferMoney);
        llStatement = findViewById(R.id.llStatement);
        llSaving = findViewById(R.id.llSaving);
        llWithdrawSaving = findViewById(R.id.llWithdrawSaving);
        llHistoryInterestRate = findViewById(R.id.llHistoryInterestRate);
    }

    // Khởi tạo dữ liệu
    public void initData() {
        setupToolbar();

        config = new Config(this);

        FirebaseRecyclerOptions<TaiKhoanLienKet> danhSachTaiKhoanLienKet = DbHelper.getAffiliateAccounts(config.getCustomerKey());
        manageAccountAndCardAdapter = new ManageAccountAndCardAdapter(danhSachTaiKhoanLienKet, this);
        vp2AccountsAndCards.setAdapter(manageAccountAndCardAdapter);
        ci3.setViewPager(vp2AccountsAndCards);

        //Log.d("firebase", "Data First: " + manageAccountAndCardAdapter.getSnapshots().size());
    }

    // Xử lý sự kiện
    public void initListener() {
        vp2AccountsAndCards.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // ví trí item hiện tại đang hiển thị
                int idexCurrentItem = vp2AccountsAndCards.getCurrentItem();

                // Lấy Tài khoản hiện tại đang hiển thị
                currentAccountCardInPage = manageAccountAndCardAdapter.getItem(idexCurrentItem);

                // Lấy key của tài khoản
                taiKhoanKey = manageAccountAndCardAdapter.getRef(idexCurrentItem).getKey();

                // Hiển thị button theo loại tài khoản
                hideButtonAccountType(); // ẩn tất cả button cũ
                if (currentAccountCardInPage.getMaLoaiTKKey().equals("0")) // tạm thời gắn cứng
                    showButtonPaymentAccountType();
                else
                    showButtonSavingAccountType();
            }
        });
    }

    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Quản lý thẻ và tài khoản");
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
        tbToolbar.addView(textView);

        // kích hoạt nút quay lại trên ActionBar
        if (getSupportActionBar() != null) {
            // Đặt màu trắng cho nút quay lại
            final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_chevron_left_24);
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            // Hiển thị nút quay lại
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    // xử lý sự kiện ấn nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Kết thúc Activity hiện tại và quay lại Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            tvSurplus.setText(taiKhoanLienKet.getSoDuFormat() + "đ");
            iconSurplus.setImageResource(R.drawable.eye_slash_solid);
        } else { // không mã hóa
            tvSurplus.setText("*******đ");
            iconSurplus.setImageResource(R.drawable.baseline_remove_red_eye_24);
        }
    }

    // Hiển thị button theo loại thẻ thanh toán
    public void showButtonPaymentAccountType() {
        llSettingCard.setVisibility(View.VISIBLE);
        llHistoryTransferMoney.setVisibility(View.VISIBLE);
        llStatement.setVisibility(View.VISIBLE);
    }

    // Hiển thị button theo loại thẻ tiết kiệm
    public void showButtonSavingAccountType() {
        llSaving.setVisibility(View.VISIBLE);
        llWithdrawSaving.setVisibility(View.VISIBLE);
        llHistoryInterestRate.setVisibility(View.VISIBLE);
    }

    // Ẩn tất cả button
    public void hideButtonAccountType() {
        llSettingCard.setVisibility(View.GONE);
        llHistoryTransferMoney.setVisibility(View.GONE);
        llStatement.setVisibility(View.GONE);
        llSaving.setVisibility(View.GONE);
        llWithdrawSaving.setVisibility(View.GONE);
        llHistoryInterestRate.setVisibility(View.GONE);
    }
}