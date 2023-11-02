package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ReminderTransferMoneyAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.NhacChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ReminderTransferMoneyActivity extends AppCompatActivity implements ReminderTransferMoneyAdapter.Listener {

    // View
    RecyclerView rvReminderTransferMoneys;
    Toolbar tbToolbar;
    ImageView ivAddReminder;
    FirebaseRecyclerOptions<NhacChuyenTien> reminderTransferMoneyOptions;
    ReminderTransferMoneyAdapter reminderTransferMoneyAdapter;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_transfer_money);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        tbToolbar = findViewById(R.id.tbToolbar);
        rvReminderTransferMoneys = findViewById(R.id.rvReminderTransferMoneys);
        ivAddReminder = findViewById(R.id.ivAddReminder);
    }

    // khởi tạo
    private void initData() {
        setupToolbar();

        config = new Config(this);

        reminderTransferMoneyOptions = DbHelper.getReminderTransferMoneys(config.getCustomerKey());
        reminderTransferMoneyAdapter = new ReminderTransferMoneyAdapter(reminderTransferMoneyOptions, this);
        rvReminderTransferMoneys.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReminderTransferMoneys.setAdapter(reminderTransferMoneyAdapter);
    }

    // xử lý sự kiện
    private void initListener() {
        // thêm nhắc chuyển tiền
        addReminder();
    }

    // Xử lý sự kiện thêm nhắc chuyển tiền
    private void addReminder() {
        ivAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderTransferMoneyActivity.this, AddReminderTransferMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupToolbar() {
        tbToolbar.setTitle("Nhắc chuyển tiền");
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
        reminderTransferMoneyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reminderTransferMoneyAdapter.stopListening();
    }
}