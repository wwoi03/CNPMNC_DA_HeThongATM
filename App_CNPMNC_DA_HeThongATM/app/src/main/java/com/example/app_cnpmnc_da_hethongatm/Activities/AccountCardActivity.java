package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Adapter.AccountCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class AccountCardActivity extends AppCompatActivity implements AccountCardAdapter.Listener {
    // View
    RecyclerView rvAccountCard;
    Toolbar tbToolbar;
    ProgressBar progressBar;

    // Adapter
    AccountCardAdapter accountCardAdapter;

    Config config;
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

        setupToolbar();

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

    // setup toolbar
    private void setupToolbar() {
        tbToolbar.setTitle("Danh sách tài khoản");
        tbToolbar.setTitleTextColor(-1);
        setSupportActionBar(tbToolbar);

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
        if(model.getTinhTrangTaiKhoan() != 0){
            Toast.makeText(AccountCardActivity.this, "Thẻ khóa r thằng loz", Toast.LENGTH_SHORT).show();
        }
        else {
            if(flag ==10){
                Intent intent = new Intent(AccountCardActivity.this, ActivityListGD.class);
                intent.putExtra("taiKhoanNguon", model.getSoTaiKhoan());
                startActivity(intent);
            }
            else {
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
}