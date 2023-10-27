package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // View
    EditText etPhone, etPassword;
    Button btLogin;

    String fileName = "config"; // dùng để lưu shared_preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("customerPhone", "") != "") {
                etPhone.setText(sharedPreferences.getString("customerPhone", ""));
            }
        }
    }

    // Ánh xạ view
    private void initUI() {
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    // Khởi tạo dữ liệu
    private void initData() {
        sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Xử lý sự kiện
    private void initListener() {
        // xứ lý khi bấm nút đăng nhập
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(etPhone.getText().toString(), etPassword.getText().toString())) { // kiểm tra chuỗi có rỗng hay không
                    String email = etPhone.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    checkAccount(email, password); // kiểm tra tài khoản
                }
            }
        });
    }

    // Kiểm tra chuỗi
    private boolean isEmpty(String phone, String password) {
        if (phone.isEmpty()) {
            toastMessage("Vui lòng nhập số điện thoại");
        } else if (password.isEmpty()) {
            toastMessage("Vui lòng nhập mật khẩu");
        } else {
            return true;
        }
        return false;
    }

    // Toast
    private void toastMessage(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void checkAccount(String phone, String password) {
        Query customer = DbHelper.firebaseDatabase.getReference("KhachHang").orderByChild("SoDienThoai").equalTo(phone);

    customer.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);

                    Log.d("firebase", khachHang.getCCCD());
                    if (khachHang != null) {
                        // Kiểm tra mật khẩu
                        if (password.equals(khachHang.getMatKhau())) {
                            // lưu dữ liệu vào Shareference
                            editor.putString("customerPhone", phone);
                            editor.putString("customerKey", dataSnapshot.getKey());
                            editor.putString("customerName", khachHang.getTenKH());
                            editor.commit();

                            startProgressBar(1500);
                        } else {
                            toastMessage("Mật khẩu không chính xác");
                        }
                    }
                }
            } else {
                toastMessage("Thông tin tài khoản hoặc mật khẩu không chính xác!");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }

    // chuyển qua trang chủ
    private void homePage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // chạy ProgressBar
    private void startProgressBar(long delayTime) {
        showProgressBar();

        // Sử dụng Handler để đóng vòng quay sau 1 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng vòng quay sau 1 giây
                hideProgressBar();
                homePage();
            }
        }, delayTime);
    }

    // hiển thị ProgressBar
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    // ẩn ProgressBar
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}