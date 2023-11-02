package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;

public class ConfirmReminderTransferMoneyActivity extends AppCompatActivity {
    // View
    Toolbar tbToolbar;
    TextView tvContent, tvDateLimit, tvBeneficiary, tvMoney;
    Button btConfirm;

    // var
    Config config;
    TaiKhoanLienKet taiKhoanNhan;
    String content, dateLimit;
    double money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reminder_transfer_money);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        tbToolbar = findViewById(R.id.tbToolbar);
        tvContent = findViewById(R.id.tvContent);
        tvBeneficiary = findViewById(R.id.tvBeneficiary);
        tvMoney = findViewById(R.id.tvMoney);
        tvDateLimit = findViewById(R.id.tvDateLimit);
        btConfirm = findViewById(R.id.btConfirm);
    }

    // khởi tạo dữ liệu
    private void initData() {
        setupToolbar();

        config = new Config(this);

        // Bắt dữ liệu intent
        Intent getDataIntent = getIntent();
        taiKhoanNhan = (TaiKhoanLienKet) getDataIntent.getSerializableExtra("taiKhoanNhan");
        content = (String) getDataIntent.getSerializableExtra("content");
        dateLimit = (String) getDataIntent.getSerializableExtra("dateLimit");
        money = (double) getDataIntent.getSerializableExtra("money");

        // hiển thị thông tin
        showInfoConfirm();
    }

    // lắng nghe xự kiện
    private void initListener() {
        onClickConfirm();
    }

    // setup toolbar
    private void setupToolbar() {
        tbToolbar.setTitle("Xác nhận");
        tbToolbar.setTitleTextColor(-1);
        setSupportActionBar(tbToolbar);

        // kích hoạt nút quay lại trên ActionBar
        if (getSupportActionBar() != null) {
            // Đặt màu trắng cho nút quay lại
            final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_chevron_left_24);
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            // Hiển thị nút quay lại
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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

    // hiển thị thông tin
    private void showInfoConfirm() {
        tvContent.setText(content);
        tvDateLimit.setText(dateLimit);
        tvBeneficiary.setText(taiKhoanNhan.getTenTK() + " - " + taiKhoanNhan.getSoTaiKhoan());
        tvMoney.setText(String.format("%,d", (long) (money)));
    }

    // xử lý sự kiệm khi bấm vào nút "Xác nhận"
    private void onClickConfirm() {
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper.addReminderTransferMoney(config.getCustomerKey(), taiKhoanNhan, content, dateLimit, money, new DbHelper.FirebaseListener() {
                    @Override
                    public void onSuccessListener() {
                        Intent intent = new Intent(ConfirmReminderTransferMoneyActivity.this, ReminderTransferMoneyActivity.class);
                        /*
                        * FLAG_ACTIVITY_CLEAR_TOP: Nếu Activity bạn muốn khởi động đã tồn tại trong stack, nó sẽ được mang lên đầu stack và tất
                        * cả các Activity ở trên nó sẽ bị hủy. Nếu Activity không tồn tại, một thể hiện mới của nó sẽ được tạo ra.
                        * */
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailureListener(Exception e) {

                    }

                    @Override
                    public void onSuccessListener(DataSnapshot snapshot) {

                    }
                });
            }
        });
    }
}