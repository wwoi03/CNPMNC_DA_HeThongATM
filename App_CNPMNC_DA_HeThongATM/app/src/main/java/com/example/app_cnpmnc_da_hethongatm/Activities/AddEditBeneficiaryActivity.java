package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;

public class AddEditBeneficiaryActivity extends AppCompatActivity {
    // View
    EditText etNameBeneficiary, etAccountBeneficiary;
    Button btEdit;
    Config config;
    ThuHuong thuHuong;
    String thuHuongKey;
    int flag; // biến cờ kiểm tra thao tác Thêm (1) /Sửa (2)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_beneficiary);

        initUI();
        initData();
        initListener();
    }

    // Ánh xạ view
    public void initUI() {
        etNameBeneficiary= findViewById(R.id.etNameBeneficiary);
        etAccountBeneficiary= findViewById(R.id.etAccountBeneficiary);
        btEdit = findViewById(R.id.btEdit);
    }

    // Khởi tạo dữ liệu
    public void initData() {
        config = new Config(this);

        // Bắt intent
        Intent getDataIntent = getIntent();
        flag = (int) getDataIntent.getSerializableExtra("flag");

        if (flag == BeneficiaryManagementActivity.EDIT_BENEFICIARY_FLAG) {
            thuHuong = (ThuHuong) getDataIntent.getSerializableExtra("editThuHuong");
            thuHuongKey = (String) getDataIntent.getSerializableExtra("thuHuongKey");

            // hiện thị thị thông tin người thụ hưởng
            etNameBeneficiary.setText(thuHuong.getTenNguoiThuHuong());
            etAccountBeneficiary.setText(String.valueOf(thuHuong.getTKThuHuong()));

            btEdit.setText("Cập nhật");
        }
    }

    // Xử lý sự kiện
    public void initListener() {
        // xử lý bấm nút cập nhật
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etNameBeneficiary.getText().toString().trim();
                long newAccount = Long.parseLong(etAccountBeneficiary.getText().toString().trim());

                ThuHuong newThuHuong = new ThuHuong(null, config.getCustomerKey(), newAccount, newName);

                // Sửa thụ hưởng
                if (flag == BeneficiaryManagementActivity.EDIT_BENEFICIARY_FLAG) {
                    updateBeneficiary(newThuHuong);
                }

                // Thêm thụ hưởng
                if (flag == BeneficiaryManagementActivity.ADD_BENEFICIARY_FLAG) {
                    addBeneficiary(newThuHuong);
                }
            }
        });
    }

    // Sửa thụ hưởng
    private void updateBeneficiary(ThuHuong newThuHuong) {
        DbHelper.updateBeneficiary(newThuHuong, thuHuongKey, this, new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {
                Toast.makeText(AddEditBeneficiaryActivity.this, "Đã chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailureListener(Exception e) {
                Toast.makeText(AddEditBeneficiaryActivity.this, "Chỉnh sửa thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {

            }
        });
    }

    // Thêm thụ hưởng
    private void addBeneficiary(ThuHuong newThuHuong) {
        DbHelper.addBeneficiary(newThuHuong, this ,new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {
                Toast.makeText(AddEditBeneficiaryActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailureListener(Exception e) {
                Toast.makeText(AddEditBeneficiaryActivity.this, "Thêm thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {

            }
        });
    }
}