package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText etOldpassword , etNewpassword, etConfirmpass;
    Button btChangePassword;
    String  oldpasswordString, newpasswordString, confirmpassString;
    Toolbar tbToolbar;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initUI();
        initData();
        initListener();
    }

    //anh xa view
    private void initUI() {
        etOldpassword = findViewById(R.id.etOldpassword);
        etNewpassword = findViewById(R.id.etNewpassword);
        etConfirmpass = findViewById(R.id.etConfirmpass);
        btChangePassword = findViewById(R.id.btChangePassword);
        tbToolbar = findViewById(R.id.tbToolbar);
    }

    // data
    private void initData() {
        config = new Config(this);
        UtilityClass.setupToolbar(ChangePasswordActivity.this, tbToolbar, "Đổi mật khẩu");
    }

    // xự kiện
    private void initListener() {
        // đổi mật khẩu
        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kiểm tra
                if (checkValidation() == true) {
                    changePassword();
                }
            }
        });
    }

    // kiểm tra rỗng
    public boolean checkValidation() {
        oldpasswordString = etOldpassword.getText().toString().trim();
        confirmpassString = etConfirmpass.getText().toString().trim();
        newpasswordString = etNewpassword.getText().toString().trim();

        if (oldpasswordString.isEmpty()) {
            UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Vui lòng nhập mật khẩu cũ!");
            return false;
        } else if (newpasswordString.isEmpty()) {
            UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Vui lòng nhập mật khẩu mới!");
            return false;
        } else if (confirmpassString.isEmpty()) {
            UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Vui lòng xác nhận mật khẩu!");
            return false;
        } else if (newpasswordString.equals(confirmpassString) == false) {
            UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Mật khẩu xác nhận không chính xác!");
            return false;
        } else if (newpasswordString.length() < 8) {
            UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Mật khẩu mới không được nhỏ hơn 8 ký tự!");
            return false;
        }   else {
            return true;
        }
    }

    private void changePassword() {
        DbHelper.firebaseDatabase.getReference("KhachHang").child(config.getCustomerKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            KhachHang khachHang = snapshot.getValue(KhachHang.class);

                            // kiểm tra mật khẩu cũ nhập đúng
                            if (khachHang.getMatKhau().equals(oldpasswordString)) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("MatKhau", newpasswordString);

                                DbHelper.firebaseDatabase.getReference("KhachHang").child(config.getCustomerKey()).updateChildren(map);

                                showDialogUpdateSusscess("Đổi mật khẩu thành công.");
                            } else {
                                UtilityClass.showDialogError(ChangePasswordActivity.this, "Lỗi", "Mật khẩu cũ không chính xác!");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // hiển thị cập nhật thành công
    private void showDialogUpdateSusscess(String text) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Cập nhật")
                .setContentText(text)
                .setConfirmText("Đồng ý!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
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
}