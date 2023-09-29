package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;

public class updateName extends AppCompatActivity {

    private SacombankAPIManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiManager = new SacombankAPIManager();

        // Khi bạn muốn cập nhật tên tài khoản
        updateAccountName("your_access_token", "your_account_id", "New Account Name");
    }

    private void updateAccountName(String accessToken, String accountId, String newAccountName) {
        apiManager.updateAccountName(newAccountName, new SacombankAPIManager.OnAccountNameUpdateListener() {
            @Override
            public void onSuccess() {
                // Xử lý khi cập nhật tên tài khoản thành công
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(updateName.this, "Cập nhật tên tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(final String errorMessage) {
                // Xử lý khi cập nhật tên tài khoản thất bại
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(updateName.this, "Cập nhật tên tài khoản thất bại: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
