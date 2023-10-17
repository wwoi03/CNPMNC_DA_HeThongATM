package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.app_cnpmnc_da_hethongatm.Adapter.AccountCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class AccountCardActivity extends AppCompatActivity implements AccountCardAdapter.Listener {
    // View
    RecyclerView rvAccountCard;

    // Adapter
    AccountCardAdapter accountCardAdapter;

    Config config;

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
        rvAccountCard = findViewById(R.id.rvAccountCard);
    }

    // khởi tạo dữ liệu
    public void initData() {
        config = new Config(this);

        FirebaseRecyclerOptions<TaiKhoanLienKet> affiliateAccountOptions = DbHelper.getAffiliateAccounts(config.getCustomerKey());
        accountCardAdapter = new AccountCardAdapter(affiliateAccountOptions, this);
        rvAccountCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAccountCard.setAdapter(accountCardAdapter);
    }

    // xử lý sự kiện
    public void initListener() {

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
        Intent intent = getIntent();
        intent.putExtra("taiKhoanNguon", model);
        intent.putExtra("taiKhoanNguonKey", databaseReference.getKey());
        setResult(TransferMoneyActivity.CHOOSE_SOURCE_ACCOUNT, intent);
        finish();
    }
}