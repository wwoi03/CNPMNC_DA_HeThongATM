package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BeneficiaryManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_management);

        initUI();
        initData();
        initListener();
    }

    // Ánh xạ view
    private void initUI() {

    }

    // Khởi tạo dữ liệu
    private void initData() {
        FirebaseRecyclerOptions<ThuHuong> beneficiaryOptions = DbHelper.getBeneficiaries(987654321);


    }

    // Xử lý sự kiện
    private void initListener() {
    }
}