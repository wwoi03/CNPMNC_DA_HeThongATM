package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditAccountNicknameActivity extends AppCompatActivity {
    TextView tvSourceAccount;
    EditText etNewNickname;
    Button btConfirm;
    Toolbar tbToolbar;
    Config config;

    String taiKhoanNguonKey = "", newNicknameString;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == ResultCode.EDIT_NICKNAME) {
                        TaiKhoanLienKet taiKhoanLienKet = (TaiKhoanLienKet) result.getData().getSerializableExtra("taiKhoanNguon");

                        taiKhoanNguonKey = taiKhoanLienKet.getKey();

                        etNewNickname.setText(taiKhoanLienKet.getTenTK());
                        tvSourceAccount.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_nickname);

        initUI();
        initData();
        initListener();
    }

    private void initUI() {
        tvSourceAccount = findViewById(R.id.tvSourceAccount);
        etNewNickname = findViewById(R.id.etNewNickname);
        btConfirm = findViewById(R.id.btConfirm);
        tbToolbar = findViewById(R.id.tbToolbar);
    }

    private void initData() {
        config = new Config(this);
        UtilityClass.setupToolbar(EditAccountNicknameActivity.this, tbToolbar, "Đặt nickname yêu thích");
    }

    private void initListener() {
        // xử lý bấm chọn tài khoản
        tvSourceAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountNicknameActivity.this, AccountCardActivity.class);
                intent.putExtra("flag", ResultCode.EDIT_NICKNAME);
                launcher.launch(intent);
            }
        });

        // xác nhận đổi nickname
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNicknameString = etNewNickname.getText().toString().trim();

                if (taiKhoanNguonKey.isEmpty()) { // kiểm tra chưa chọn tài khoản
                    UtilityClass.showDialogError(EditAccountNicknameActivity.this, "Lỗi", "Vui lòng chọn tài khoản!");
                } else if (newNicknameString.isEmpty()) { // kiểm tra chưa nhập nickname
                    UtilityClass.showDialogError(EditAccountNicknameActivity.this, "Lỗi", "Vui lòng nhập nickname mới!");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("TenTK", newNicknameString);

                    DbHelper.firebaseDatabase.getReference("TaiKhoanLienKet").child(taiKhoanNguonKey).updateChildren(map);

                    showDialogUpdateSusscess("Đặt nickname mới thành công.");
                }
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