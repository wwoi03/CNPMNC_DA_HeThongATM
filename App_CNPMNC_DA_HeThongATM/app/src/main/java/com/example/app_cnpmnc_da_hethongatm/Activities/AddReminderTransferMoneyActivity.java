package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddReminderTransferMoneyActivity extends AppCompatActivity {
    // View
    Toolbar tbToolbar;
    EditText etContent, etMoney, etBeneficiary, etDateLimit;
    TextView tvBeneficiaryName;
    ImageView ivDateLimitIcon;
    Button btNext;
    RelativeLayout rlInfoBeneficiary;
    ConstraintLayout clMainLayout;

    // var
    int mYear, mMonth, mDay;
    TaiKhoanLienKet taiKhoanNhan;
    String content, moneyString, beneficiary, dateLimit;
    double money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_transfer_money);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        clMainLayout = findViewById(R.id.clMainLayout);
        tbToolbar = findViewById(R.id.tbToolbar);
        etContent = findViewById(R.id.etContent);
        etMoney = findViewById(R.id.etMoney);
        etBeneficiary = findViewById(R.id.etBeneficiary);
        etDateLimit = findViewById(R.id.etDateLimit);
        ivDateLimitIcon = findViewById(R.id.ivDateLimitIcon);
        btNext = findViewById(R.id.btNext);
        rlInfoBeneficiary = findViewById(R.id.rlInfoBeneficiary);
        tvBeneficiaryName = findViewById(R.id.tvBeneficiaryName);
    }

    // khởi tạo dữ liệu
    private void initData() {
        setupToolbar();
    }

    // lắng nghe xự kiện
    private void initListener() {
        onClickDateLimit();
        onClickButtonNext();
    }

    // setup toolbar
    private void setupToolbar() {
        tbToolbar.setTitle("Thêm mới nhắc chuyển tiền");
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

    // xử lý khi bấm nút "Tiếp tục"
    private void onClickButtonNext() {
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = String.valueOf(etContent.getText()).trim();
                moneyString = String.valueOf(etMoney.getText()).trim();
                beneficiary = String.valueOf(etBeneficiary.getText()).trim();
                dateLimit = String.valueOf(etDateLimit.getText()).trim();

                if (checkEmpty() == true) { // kiểm tra rỗng
                    money = Double.parseDouble(moneyString);

                    if (checkDateLimitIsValid() == false) { // kiểm tra ngày nhập hợp lệ
                        etDateLimit.setText("");
                        buildAlertDialog("Vui lòng nhập đúng định dạng dd/mm/yyyy");
                    } else if (checkDateLimitAfterCurrentDate(dateLimit) == false) { // kiểm tra ngày phải đến sau ngày hiện tại
                        buildAlertDialog("Ngày đến hạn phải có sau ngày hiện tại");
                    } else if (money < 1000) { // kiểm tra số tiền phải lớn hơn 1000
                        buildAlertDialog("Số tiền nhập phải lớn hơn 1000");
                    } else {
                        checkExistsBeneficiary();
                    }
                }
            }
        });
    }

    // kiểm tra nhập hợp lệ
    private boolean checkEmpty() {
        if (content == null || content.equals("")) { // kiểm tra nội dung
            buildAlertDialog("Vui lòng nhập lời nhắc");
            return false;
        } else if (dateLimit == null || dateLimit.equals("")) { // kiểm tra ngày đến hạn
            buildAlertDialog("Vui lòng nhập ngày đến hạn");
            return false;
        } else if (moneyString == null || moneyString.equals("")) { // kiểm tra số tiền nhập
            buildAlertDialog("Vui lòng nhập số tiền");
            return false;
        } else if (beneficiary == null || beneficiary.equals("")) { // kiểm tra người thụ hưởng
            buildAlertDialog("Vui lòng nhập người thụ hưởng");
            return false;
        }

        return true;
    }

    // kiểm tra người thụ hưởng
    private void checkExistsBeneficiary() {
        long soTaiKhoan = Long.parseLong(String.valueOf(etBeneficiary.getText()).trim());
        DbHelper.getAccountByAccountNumber(soTaiKhoan, new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {

            }

            @Override
            public void onFailureListener(Exception e) {

            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {
                if (snapshot.exists()) { // tồn tại
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        taiKhoanNhan = dataSnapshot.getValue(TaiKhoanLienKet.class);
                        showInfoBeneficiary();

                        Intent intent = new Intent(AddReminderTransferMoneyActivity.this, ConfirmReminderTransferMoneyActivity.class);
                        intent.putExtra("taiKhoanNhan", taiKhoanNhan);
                        intent.putExtra("content", content);
                        intent.putExtra("money", money);
                        intent.putExtra("dateLimit", dateLimit);
                        startActivity(intent);
                        break;
                    }
                } else { // không tồn tại
                    hiddenInfoBeneficiary();
                    buildAlertDialog("Tài khoản thụ hưởng không tồn tại");
                }
            }
        });
    }

    // xử lý khi bấm ngày hết hạn
    private void onClickDateLimit() {
        ivDateLimitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReminderTransferMoneyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dateLimitString = dateFormat(year, month, dayOfMonth);
                                etDateLimit.setText(dateLimitString);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    // dialog
    public void buildAlertDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (text != null || text.equals("")) {
            builder.setTitle("Có Lỗi");
            builder.setMessage(text);
        } else {
            builder.setTitle("Thành công");
        }

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (text != null || text.equals("")) {

                } else {
                    finish();
                }

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // kiểm tra quá đến hạn
    public boolean checkDateLimitAfterCurrentDate(String dateLimitString) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.parse(dateLimitString, formatter);
        }

        LocalDate now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDate.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (date.isAfter(now)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // chuyển định dạng ngày
    private String dateFormat(int year, int month, int dayOfMonth) {
        return String.format("%02d", dayOfMonth + 1) + "/" + String.format("%02d", month + 1) + "/" + year;
    }

    // kiểm tra ngày nhập hợp lệ
    private boolean checkDateLimitIsValid() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(dateLimit);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // hiển thị thông tin thụ hưởng
    private void showInfoBeneficiary() {
        rlInfoBeneficiary.setVisibility(View.VISIBLE);
        tvBeneficiaryName.setText(taiKhoanNhan.getTenTK());
    }

    // ẩn thông tin thụ hưởng
    private void hiddenInfoBeneficiary() {
        rlInfoBeneficiary.setVisibility(View.GONE);
        tvBeneficiaryName.setText("");
    }
}