package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.app_cnpmnc_da_hethongatm.R;

public class AccountSettingsActivity extends AppCompatActivity {
    // View
    LinearLayout llPersonalInfo, llEditNickName, llChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        initUI();
        initData();
        initListener();
    }

    // Ánh xạ view
    public void initUI() {
        llPersonalInfo = findViewById(R.id.llPersonalInfo);
        llEditNickName = findViewById(R.id.llEditNickName);
        llChangePassword = findViewById(R.id.llChangePassword);
    }

    // khởi tạo dữ liệu
    public void initData() {
        // Xử lý khi bầm vào thông tin cá nhân
        llPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Xử lý khi bầm vào sửa nickname
        llEditNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingsActivity.this, EditAccountNicknameActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý khi bầm vào đổi mật khẩu
        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingsActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    // xử lý sự kiện
    public void initListener() {

    }
}