package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.R;

public class SacombankAPIManager extends AppCompatActivity {
    private SacombankAPIManager apiManager;
    private EditText etAccountName;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAccountName = findViewById(R.id.et_account_name);

        // Thiết lập ActionBar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_save) {
            // Khi người dùng nhấn nút Lưu
            String accessToken = "your_access_token";
            String accountId = "your_account_id";
            String newAccountName = etAccountName.getText().toString();

            updateAccountName(accessToken, accountId, newAccountName);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAccountName(String accessToken, String accountId, String newAccountName) {
        apiManager.updateAccount(accessToken,
                accountId,
                newAccountName,
                new SacombankAPIManager.OnAccountUpdateListener() {
                    @Override
                    public void onSuccess() {
                        // Xử lý khi cập nhật tên tài khoản thành công
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SacombankAPIManager.this, "Cập nhật tên tài khoản thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
        ;
    }

    private void updateAccount(String accessToken, String accountId, String newAccountName, SacombankAPIManager.OnAccountUpdateListener onAccountUpdateListener) {

    }

    public void updateAccountName(String newAccountName, SacombankAPIManager.OnAccountNameUpdateListener onAccountNameUpdateListener) {

    }

    public interface OnAccountUpdateListener {
        void onSuccess();
    }

    public interface OnAccountNameUpdateListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
