package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // View
    EditText etPhone, etPassword;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initData();
        initListener();
    }

    // Ánh xạ view
    private void initUI() {
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
    }

    // Khởi tạo dữ liệu
    private void initData() {

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

    public boolean checkAccount(String phone, String password) {
        Query customer = DbHelper.firebaseDatabase.getReference("KhachHang").child("SoDienThoai").equalTo(phone);

        customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for ()
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        return true;
    }
}