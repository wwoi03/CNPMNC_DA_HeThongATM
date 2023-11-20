package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ListBeneficiaryAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiGiaoDich;
import com.example.app_cnpmnc_da_hethongatm.Model.MauChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.NhacChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TransferMoneyActivity extends AppCompatActivity {
    // View
    ImageView ivBeneficiary;
    TextView tvSurplus, tvNameBeneficiary, tvSourceAccount;
    TextInputEditText etMoney, etContent, etAccountBeneficiary;
    Button btTransferMoney;
    ProgressBar progressBar;
    Toolbar tbToolbar;
    String MaGD="";
    MauChuyenTien mauChuyenTien = new MauChuyenTien();
    // Flag
    public static int CHOOSE_SOURCE_ACCOUNT = 101;

    // var
    String taiKhoanNguonKey = "", taiKhoanHuongKey;
    TaiKhoanLienKet taiKhoanNguon, taiKhoanHuong;
    NhacChuyenTien nhacChuyenTien;

    ThuHuong thuHuong;
    int flag;
    int flagSaveBill;
    String maLoaiGGKey;
    Config config;
    boolean checkvalid = false;

    Intent getDataIntent;
    Context context;

    String moneyString;
    boolean isCheckvalid = false;
    boolean isTransferMoney = false;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == CHOOSE_SOURCE_ACCOUNT) {
                        taiKhoanNguon = (TaiKhoanLienKet) result.getData().getSerializableExtra("taiKhoanNguon");

                        checkSourceAccount(taiKhoanNguon.getSoTaiKhoan());
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        initUI();
        initData();
        initListener();
    }

    private void UpdateTaiKhoanNguon(){

    }

    // ánh xạ view
    public void initUI() {
        tbToolbar = findViewById(R.id.tbToolbar);
        progressBar = findViewById(R.id.progressBar);
        tvSourceAccount = findViewById(R.id.tvSourceAccount);
        ivBeneficiary = findViewById(R.id.ivBeneficiary);
        tvSurplus = findViewById(R.id.tvSurplus);
        tvNameBeneficiary = findViewById(R.id.tvNameBeneficiary);
        etMoney = findViewById(R.id.etMoney);
        etContent = findViewById(R.id.etContent);
        etAccountBeneficiary = findViewById(R.id.etAccountBeneficiary);
        btTransferMoney = findViewById(R.id.btTransferMoney);
    }

    // khởi tạo dữ liệu
    public void initData() {
        config = new Config(this);

        context = TransferMoneyActivity.this;

        setupToolbar();

        taiKhoanNguon = new TaiKhoanLienKet();
        taiKhoanHuong = new TaiKhoanLienKet();

        getDataIntent = getIntent();

        if (getDataIntent.hasExtra("flag") == true) {
            flag = (int) getDataIntent.getSerializableExtra("flag");

            if (flag == BeneficiaryManagementActivity.USER_NAME) { // thụ hưởng
                thuHuong = (ThuHuong) getDataIntent.getSerializableExtra("tkthuhuong");
                etAccountBeneficiary.setText(String.valueOf(thuHuong.getTKThuHuong()));
            } else if (flag == ResultCode.LUU_MAU_CHUYEN_TIEN) { // mẫu chuyển tiền
                long a =(long) getDataIntent.getSerializableExtra("STK123");
                double b =(double) getDataIntent.getSerializableExtra("SoTien123");
                String c = (String) getDataIntent.getSerializableExtra("NoiDung123");
                etAccountBeneficiary.setText(String.valueOf(a));
                etMoney.setText(String.valueOf(b));
                etContent.setText(c);
            } else if (flag == ResultCode.SCAN_QR) { // quét QR
                getIntentFromQRCode();
            } else if (flag == ResultCode.ACCOUNT_TRANSFER_MONEY) { // tài khoản chuyển tiền
                long soTaiKhoan = (long) getDataIntent.getSerializableExtra("soTaiKhoan");

                checkSourceAccount(soTaiKhoan);
            } else if (flag == ResultCode.TRANSFER_MONEY_REMINDER) {
                nhacChuyenTien = (NhacChuyenTien) getDataIntent.getSerializableExtra("nhacChuyenTien");

                etMoney.setText(String.valueOf(nhacChuyenTien.getSoTienNhacChuyen()));
                etAccountBeneficiary.setText(String.valueOf(nhacChuyenTien.getTaiKhoanNhan()));
                etContent.setText(nhacChuyenTien.getNoiDungNhac());
            }
        }
    }


    // xử lý sự kiện
    public void initListener() {
        // xử lý chọn thẻ nguồn chuyển tiền
        tvSourceAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferMoneyActivity.this, AccountCardActivity.class);
                launcher.launch(intent);
            }
        });
        // Xử lý sự kiện trên trường nhập số tài khoản hưởng
        etAccountBeneficiary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkThuHuong();
                }
            }
        });

        // xử lý ấn "Tiếp tục"
        btTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkThuHuong();

                // kiểm tra tổng
                moneyString = etMoney.getText().toString().trim();
                String accountBeneficiaryString = etAccountBeneficiary.getText().toString().trim();
                if (taiKhoanNguonKey.isEmpty()) {
                    UtilityClass.showDialogError(context, "Lỗi", "Vui lòng chọn tài khoản nguồn!");
                } else if (accountBeneficiaryString.isEmpty()) {
                    UtilityClass.showDialogError(context, "Lỗi", "Vui lòng nhập tài khoản hưởng!");
                } else if (accountBeneficiaryString.isEmpty()) {
                    UtilityClass.showDialogError(context, "Lỗi", "Vui lòng nhập người nhận!");
                } else if (moneyString.isEmpty()) { // rỗng
                    UtilityClass.showDialogError(context, "Lỗi", "Vui lòng nhập số tiền cần chuyển!");
                } else if(Double.parseDouble(moneyString) > taiKhoanNguon.getSoDu()){
                    UtilityClass.showDialogError(context, "Lỗi", "Số dư không đủ giao dịch!");
                }else if (!GetDate().equals(taiKhoanNguon.getNgayGD())) {
                    taiKhoanNguon.setNgayGD(GetDate());
                    taiKhoanNguon.setTienDaGD(0);
                } else if (Double.parseDouble(moneyString) + taiKhoanNguon.getTienDaGD() > taiKhoanNguon.getHanMucTK()) {
                    UtilityClass.showDialogError(context, "Lỗi", "Số tiền giao dịch vuợt quá hạn mức!");
                } else if (Double.parseDouble(moneyString) < 1000) { // không rỗng
                    UtilityClass.showDialogError(context, "Lỗi", "Nghèo vậy thằng lol!");
                } else {
                    isCheckvalid = true;
                    checkThuHuong();
                }
            }
        });

        // bấm icon thụ hưởng
        ivBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<ThuHuong> options =
                        new FirebaseRecyclerOptions.Builder<ThuHuong>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("ThuHuong").orderByChild("MaKHKey").equalTo(config.getCustomerKey()), ThuHuong.class)
                                .build();

                ListBeneficiaryAdapter listBeneficiaryAdapter = new ListBeneficiaryAdapter(options);

                DialogPlus dialogPlus = DialogPlus.newDialog(TransferMoneyActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.activity_thuhuongtransfer))
                        .setExpanded(true, 800)
                        .setOverlayBackgroundResource(android.R.color.transparent) // Đặt màu nền trong suốt
                        .create();

                // Tìm RecyclerView trong layout của DialogPlus
                RecyclerView recyclerView = dialogPlus.getHolderView().findViewById(R.id.rc_thuhuongtransfer);

                // Thiết lập ListBeneficiaryAdapter cho RecyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(TransferMoneyActivity.this));
                recyclerView.setAdapter(listBeneficiaryAdapter);

                listBeneficiaryAdapter.startListening();
                listBeneficiaryAdapter.setOnItemClickListener(new ListBeneficiaryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ThuHuong model) {
                        long tkThuHuongLong = model.getTKThuHuong();
                        String tkThuHuongStr = Long.toString(tkThuHuongLong);
                        etAccountBeneficiary.setText(tkThuHuongStr);
                        tvNameBeneficiary.setText(model.getTenNguoiThuHuong());
                        dialogPlus.dismiss();// đóng dialogplus
                    }
                });
                dialogPlus.show();
            }
        });
    }

    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Chuyển tiền");
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

    // chuyển tiền
    private void transferMoney(double money, String noiDungChuyenKhoan,String ngaygd,double tiendaGD) {
        isTransferMoney = true;
        DbHelper.updateSurplus(taiKhoanNguonKey, taiKhoanNguon.getSoDu() - money,ngaygd,tiendaGD); // tài khoản nguồn
        DbHelper.updateSurplus(taiKhoanHuongKey, taiKhoanHuong.getSoDu() + money); // tài khoản hưởng
        MaGD = DbHelper.addTransactionHistory(taiKhoanNguon, taiKhoanHuong, money, noiDungChuyenKhoan,"0");
        BuildAlertDialogSuccess(taiKhoanNguon.getSoDu() - money);
    }
    // Toast
    private void toastMessage(String text) {
        Toast.makeText(TransferMoneyActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void BuildAlertDialog(String TenLoi){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Có Lỗi");
        builder.setMessage(TenLoi);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void BuildAlertDialogSuccess(double tien){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        long sodu = (long) taiKhoanNguon.getSoDu();
        builder.setTitle("Chuyển tiền thành công");
        builder.setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TransferMoneyActivity.this, SaveBillActivity.class);
                intent.putExtra("NguoiGui",taiKhoanNguon);
                intent.putExtra("NguoiNhan",taiKhoanHuong);
                intent.putExtra("NgayGui",GetDate());
                intent.putExtra("GioGui",GetTime());
                intent.putExtra("NoiDung",etContent.getText().toString().trim());
                intent.putExtra("MaGd",MaGD);
                String tiengd = String.valueOf(sodu - tien);
                intent.putExtra("TienGD",tiengd);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private String GetDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }
    private String GetTime(){
        LocalTime now = null;
        String timeString="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalTime.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timeString = now.format(formatter);
        }
        return timeString;
    }

    public void getIntentFromQRCode(){
        String qrCodeData = getDataIntent.getStringExtra("SoTaiKhoan");
        String amount = getDataIntent.getStringExtra("amount");
        String message = getDataIntent.getStringExtra("message");
        etAccountBeneficiary.setText(qrCodeData);
        etContent.setText(message);
        etMoney.setText(amount);
    }

    // kiểm tra tài khoản nguồn
    public void checkSourceAccount(long soTaiKhoan) {
        DbHelper.getAccountByAccountNumber(soTaiKhoan, new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {

            }

            @Override
            public void onFailureListener(Exception e) {

            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        taiKhoanNguon = dataSnapshot.getValue(TaiKhoanLienKet.class);

                        taiKhoanNguonKey = taiKhoanNguon.getKey();

                        tvSourceAccount.setText(String.valueOf(taiKhoanNguon.getSoTaiKhoan()));
                        tvSurplus.setText(taiKhoanNguon.getSoDuFormat() + " VNĐ");
                        if(etContent.getText().toString().trim().isEmpty()){
                            etContent.setText(taiKhoanNguon.getTenTK() + " chuyen tien");
                        }

                        break;
                    }
                }
            }
        });
    }

    private void checkThuHuong(){
        String accountBeneficiaryString = etAccountBeneficiary.getText().toString().trim();
        // kiểm tra edit text rỗng?
        if(accountBeneficiaryString.isEmpty()){
            tvNameBeneficiary.setText("");
        }
        if (!accountBeneficiaryString.isEmpty()) {
            long accountBeneficiary = Long.parseLong(etAccountBeneficiary.getText().toString().trim());
            if(taiKhoanNguon != null){
                if(accountBeneficiary == taiKhoanNguon.getSoTaiKhoan()){
                    BuildAlertDialog("Không thể tự chuyển khoản cho bản thân");
                    tvNameBeneficiary.setText("");
                    isCheckvalid = false;
                }
                else {
                    // truy vấn đến TaiKhoanLK theo số tài khoản
                    DbHelper.firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("SoTaiKhoan").equalTo(accountBeneficiary)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            taiKhoanHuong = dataSnapshot.getValue(TaiKhoanLienKet.class);
                                            etAccountBeneficiary.setText(String.valueOf(taiKhoanHuong.getSoTaiKhoan()));
                                            tvNameBeneficiary.setText(String.valueOf(taiKhoanHuong.getTenTK()));
                                            taiKhoanHuongKey = dataSnapshot.getKey();

                                            if (isCheckvalid == true) {
                                                double money = Double.parseDouble(moneyString);
                                                if (isTransferMoney == false) {
                                                    transferMoney(money, etContent.getText().toString().trim(),taiKhoanNguon.getNgayGD(),taiKhoanNguon.getTienDaGD()+money);
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        tvNameBeneficiary.setText("");
                                        BuildAlertDialog("không tìm thấy người thụ hưởng");
                                        isCheckvalid = false;
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    isCheckvalid =false;
                                }
                            });
                }
            }
        }
    }
}