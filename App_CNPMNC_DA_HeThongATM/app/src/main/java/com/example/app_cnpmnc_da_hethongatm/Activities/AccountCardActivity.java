package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Adapter.AccountCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTaiKhoan;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AccountCardActivity extends AppCompatActivity implements AccountCardAdapter.Listener {
    // View
    RecyclerView rvAccountCard;
    Toolbar tbToolbar;
    ProgressBar progressBar;

    // Adapter
    AccountCardAdapter accountCardAdapter;
    String loaiTKKey;

    Config config;
    Context context;
    int flag =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_card);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    public void initUI() {
        progressBar = findViewById(R.id.progressBar);
        rvAccountCard = findViewById(R.id.rvAccountCard);
        tbToolbar = findViewById(R.id.tbToolbar);
    }

    // khởi tạo dữ liệu
    public void initData() {
        config = new Config(this);
        context = AccountCardActivity.this;

        UtilityClass.setupToolbar(this, tbToolbar, "Danh sách tài khoản");

        getAccountTypeByName("tiết kiệm");

        Intent intent = getIntent();
        if (intent.getData() != null) {

        }

        flag = intent.getIntExtra("flag",0);

        FirebaseRecyclerOptions<TaiKhoanLienKet> affiliateAccountOptions = DbHelper.getAffiliateAccounts(config.getCustomerKey());
        accountCardAdapter = new AccountCardAdapter(affiliateAccountOptions, this);
        rvAccountCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAccountCard.setAdapter(accountCardAdapter);
    }

    // xử lý sự kiện
    public void initListener() {

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

    @Override
    protected void onStart() {
        super.onStart();
        accountCardAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        accountCardAdapter.stopListening();
    }

    // Xử lý sự kiện bấm vào từng thẻ
    @Override
    public void setOnClickItemListener(TaiKhoanLienKet model, DatabaseReference databaseReference) {
        if (model.getMaLoaiTKKey().equals(loaiTKKey)) {
            UtilityClass.showDialogError(context, "Lỗi", "Vui lòng chọn tài khoản thanh toán!");
        } else {
            if (model.getTinhTrangTaiKhoan() == 1){
                UtilityClass.showDialogError(context, "Lỗi", "Khóa thẻ rồi thằng lol!");
            } else if (flag == 10) {
                Intent intent = new Intent(AccountCardActivity.this, ActivityListGD.class);
                intent.putExtra("taiKhoanNguon", model.getSoTaiKhoan());
                startActivity(intent);
            } else if (flag == ResultCode.EDIT_NICKNAME) {
                Intent intent = getIntent();
                intent.putExtra("taiKhoanNguon", model);
                setResult(ResultCode.EDIT_NICKNAME, intent);
                finish();
            } else {
                Intent intent = getIntent();
                intent.putExtra("taiKhoanNguon", model);
                intent.putExtra("taiKhoanNguonKey", databaseReference.getKey());
                setResult(TransferMoneyActivity.CHOOSE_SOURCE_ACCOUNT, intent);
                finish();
            }
        }
    }

    @Override
    public void adapterOnDataChange() {
        progressBar.setVisibility(View.GONE);
    }

    // lấy loại tài khoản
    public void getAccountTypeByName(String accountTypeName) {
        DbHelper.firebaseDatabase.getReference("LoaiTaiKhoan")
                .orderByChild("TenLoaiTaiKhoan")
                .equalTo(accountTypeName.toLowerCase().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                LoaiTaiKhoan loaiTaiKhoan = dataSnapshot.getValue(LoaiTaiKhoan.class);
                                loaiTKKey = loaiTaiKhoan.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}