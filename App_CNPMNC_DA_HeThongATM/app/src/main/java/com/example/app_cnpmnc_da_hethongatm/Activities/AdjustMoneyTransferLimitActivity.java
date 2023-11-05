package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.DialogPlusAccountAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AdjustMoneyTransferLimitActivity extends AppCompatActivity implements DialogPlusAccountAdapter.Listener {
    // View
    Toolbar tbToolbar;
    TextView tvTransferLimitCurrent, tvTotalTransferMoney, tvRemainingLimit, tvAccount, tvTransferLimit;
    Button btNext;
    RecyclerView rvListAccount;
    LinearLayout llInfoAccount;

    // var
    Config config;
    DialogPlusAccountAdapter dialogPlusAccountAdapter;
    DialogPlus accountDialogPlus;
    String accountNumberString, transferLimitCurrentString, totalTransferMoneyString, remainingLimitString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_money_transfer_limit);

        initUI();
        initData();
        initListener();
    }

    private void initUI() {
        tbToolbar = findViewById(R.id.tbToolbar);
        tvTransferLimitCurrent = findViewById(R.id.tvTransferLimitCurrent);
        tvTotalTransferMoney = findViewById(R.id.tvTotalTransferMoney);
        tvRemainingLimit = findViewById(R.id.tvRemainingLimit);
        tvAccount = findViewById(R.id.tvAccount);
        tvTransferLimit = findViewById(R.id.tvTransferLimit);
        btNext = findViewById(R.id.btNext);
        llInfoAccount = findViewById(R.id.llInfoAccount);
    }

    private void initData() {
        setupToolbar();

        config = new Config(this);

        FirebaseRecyclerOptions<TaiKhoanLienKet> listAccount = DbHelper.getAffiliateAccounts(config.getCustomerKey());

        dialogPlusAccountAdapter = new DialogPlusAccountAdapter(listAccount, this);
        accountDialogPlus = DialogPlus
                .newDialog(AdjustMoneyTransferLimitActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_plus_list_account))
                .setExpanded(true, 800)
                .setOverlayBackgroundResource(android.R.color.transparent) // Đặt màu nền trong suốt
                .create();

        // Tìm RecyclerView trong layout của DialogPlus
        rvListAccount = accountDialogPlus.getHolderView().findViewById(R.id.rvListAccount);

        // Thiết lập ListBeneficiaryAdapter cho RecyclerView
        rvListAccount.setLayoutManager(new LinearLayoutManager(AdjustMoneyTransferLimitActivity.this));
        rvListAccount.setAdapter(dialogPlusAccountAdapter);

        dialogPlusAccountAdapter.startListening();
    }

    private void initListener() {
        onClickChooseAccount();
    }

    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Hạn mức tài khoản");
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

        dialogPlusAccountAdapter.startListening();
    }

    // xử lý khi bấm vào chọn tài khoản
    private void onClickChooseAccount() {
        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountDialogPlus.show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        dialogPlusAccountAdapter.stopListening();
    }

    @Override
    public void onClickItemDialogPlusAccountListener(TaiKhoanLienKet taiKhoanLienKet) {
        accountNumberString = String.valueOf(taiKhoanLienKet.getSoTaiKhoan());
        transferLimitCurrentString = String.valueOf(taiKhoanLienKet.getHanMucTK());
        remainingLimitString = String.valueOf(taiKhoanLienKet.getHanMucTK() - taiKhoanLienKet.getTienDaGD());
        totalTransferMoneyString = String.valueOf(taiKhoanLienKet.getTienDaGD());

        tvAccount.setText(accountNumberString);
        tvTransferLimit.setText(taiKhoanLienKet.getNumberFormat(Double.parseDouble(transferLimitCurrentString)) + " VND");
        tvTransferLimitCurrent.setText(taiKhoanLienKet.getNumberFormat(Double.parseDouble(transferLimitCurrentString))  + " VND");
        tvRemainingLimit.setText(taiKhoanLienKet.getNumberFormat(Double.parseDouble(remainingLimitString))  + " VND");
        tvTotalTransferMoney.setText(taiKhoanLienKet.getNumberFormat(Double.parseDouble(totalTransferMoneyString))  + " VND");

        llInfoAccount.setVisibility(View.VISIBLE);

        accountDialogPlus.dismiss();
    }
}