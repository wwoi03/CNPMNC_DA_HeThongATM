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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;

public class ConfirmTransferLimitActivity extends AppCompatActivity {

    // View
    Toolbar tbToolbar;
    LinearLayout llSuccess, llInfoAccount;
    Button btConfirm, btClose;
    TextView tvTransferLimitCurrent, tvTransferLimitNew, tvTransferLimitNewSuccess, tv1;

    // Var
    Intent getDataIntent;
    String accountKey, transferLimitCurrentString, transferLimitNewString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transfer_limit);

        initUI();
        initData();
        initListener();
    }

    private void initUI() {
        tbToolbar = findViewById(R.id.tbToolbar);
        llSuccess = findViewById(R.id.llSuccess);
        llInfoAccount = findViewById(R.id.llInfoAccount);
        btConfirm = findViewById(R.id.btConfirm);
        btClose = findViewById(R.id.btClose);
        tvTransferLimitCurrent = findViewById(R.id.tvTransferLimitCurrent);
        tvTransferLimitNew = findViewById(R.id.tvTransferLimitNew);
        tvTransferLimitNewSuccess = findViewById(R.id.tvTransferLimitNewSuccess);
        tv1 = findViewById(R.id.tv1);
    }

    private void initData() {
        setupToolbar();

        getDataIntent = getIntent();

        accountKey = (String) getDataIntent.getSerializableExtra("accountKey");
        transferLimitCurrentString = (String) getDataIntent.getSerializableExtra("transferLimitCurrentString");
        transferLimitNewString = (String) getDataIntent.getSerializableExtra("transferLimitNewString");

        showInfoConfirm();
    }

    private void initListener() {
        onClickButtonConfirm();
        onClickButtonClose();
    }

    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Xác nhận thông tin");
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

    // bấm xác nhận
    private void onClickButtonConfirm() {
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper.updateTransferMoneyLimit(accountKey, Double.parseDouble(transferLimitNewString), new DbHelper.FirebaseListener() {
                    @Override
                    public void onSuccessListener() {
                        tvTransferLimitNewSuccess.setText(getNumberFormat(Double.parseDouble(transferLimitNewString)) + " VND");
                        llSuccess.setVisibility(View.VISIBLE);
                        btClose.setVisibility(View.VISIBLE);
                        btConfirm.setVisibility(View.GONE);
                        tbToolbar.setVisibility(View.GONE);
                        llInfoAccount.setVisibility(View.GONE);
                        tv1.setVisibility(View.GONE);
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

    // bấm đóng
    private void onClickButtonClose() {
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmTransferLimitActivity.this, MainActivity.class);
                /*
                 * FLAG_ACTIVITY_CLEAR_TOP: Nếu Activity bạn muốn khởi động đã tồn tại trong stack, nó sẽ được mang lên đầu stack và tất
                 * cả các Activity ở trên nó sẽ bị hủy. Nếu Activity không tồn tại, một thể hiện mới của nó sẽ được tạo ra.
                 * */
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // hiển thị thông tin
    private void showInfoConfirm() {
        tvTransferLimitCurrent.setText(getNumberFormat(Double.parseDouble(transferLimitCurrentString)) + " VND");
        tvTransferLimitNew.setText(getNumberFormat(Double.parseDouble(transferLimitNewString)) + " VND");
    }

    public String getNumberFormat(double number) {
        // Sử dụng String.format với định dạng số có dấu phân cách
        return String.format("%,d", (long) number);
    }
}