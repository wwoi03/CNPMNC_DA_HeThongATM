package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    Button btConfirm, btReturn;
    LinearLayout llSuccess;

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
        btReturn = findViewById(R.id.btReturn);
        llSuccess = findViewById(R.id.llSuccess);
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
        onClickButtonConfirm();
        onClickButtonReturn();
    }

    // setup toolbar
    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Xác nhận");
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
        tbToolbar.addView(textView);

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
        tvMoney.setText(String.format("%,d", (long) (money)) + "đ");
    }

    // xử lý sự kiệm khi bấm vào nút "Xác nhận"
    private void onClickButtonConfirm() {
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper.addReminderTransferMoney(config.getCustomerKey(), taiKhoanNhan, content, dateLimit, money, new DbHelper.FirebaseListener() {
                    @Override
                    public void onSuccessListener() {
                        tbToolbar.setVisibility(View.GONE);
                        btConfirm.setVisibility(View.GONE);
                        btReturn.setVisibility(View.VISIBLE);
                        llSuccess.setVisibility(View.VISIBLE);
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

    // xử lý bấm quay về
    private void onClickButtonReturn() {
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmReminderTransferMoneyActivity.this, ReminderTransferMoneyActivity.class);
                /*
                 * FLAG_ACTIVITY_CLEAR_TOP: Nếu Activity bạn muốn khởi động đã tồn tại trong stack, nó sẽ được mang lên đầu stack và tất
                 * cả các Activity ở trên nó sẽ bị hủy. Nếu Activity không tồn tại, một thể hiện mới của nó sẽ được tạo ra.
                 * */
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}