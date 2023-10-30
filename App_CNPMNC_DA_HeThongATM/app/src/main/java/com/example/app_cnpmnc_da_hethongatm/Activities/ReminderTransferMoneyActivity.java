package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ReminderTransferMoneyAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.NhacChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ReminderTransferMoneyActivity extends AppCompatActivity implements ReminderTransferMoneyAdapter.Listener {

    // View
    RecyclerView rvReminderTransferMoneys;
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
        rvReminderTransferMoneys = findViewById(R.id.rvReminderTransferMoneys);
    }

    // khởi tạo
    private void initData() {
        config = new Config(this);
        reminderTransferMoneyOptions = DbHelper.getReminderTransferMoneys(config.getCustomerKey());
        reminderTransferMoneyAdapter = new ReminderTransferMoneyAdapter(reminderTransferMoneyOptions, this);
        rvReminderTransferMoneys.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReminderTransferMoneys.setAdapter(reminderTransferMoneyAdapter);
    }

    // xử lý sự kiện
    private void initListener() {

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