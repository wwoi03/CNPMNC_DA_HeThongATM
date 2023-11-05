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

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiGiaoDich;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransferMoneyActivity extends AppCompatActivity {
    // View
    ImageView ivBeneficiary;
    TextView tvSurplus, tvNameBeneficiary, tvSourceAccount;
    EditText etMoney, etContent, etAccountBeneficiary;
    Button btTransferMoney;
    ProgressBar progressBar;
    Toolbar tbToolbar;

    // Flag
    public static int CHOOSE_SOURCE_ACCOUNT = 101;

    //
    String taiKhoanNguonKey = "", taiKhoanHuongKey;
    TaiKhoanLienKet taiKhoanNguon, taiKhoanHuong;

    ThuHuong thuHuong;
    int flag;
    String maLoaiGGKey;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == CHOOSE_SOURCE_ACCOUNT) {
                        taiKhoanNguon = (TaiKhoanLienKet) result.getData().getSerializableExtra("taiKhoanNguon");
                        taiKhoanNguonKey = (String) result.getData().getSerializableExtra("taiKhoanNguonKey");
                        tvSourceAccount.setText(String.valueOf(taiKhoanNguon.getSoTaiKhoan()));
                        tvSurplus.setText(String.valueOf(taiKhoanNguon.getSoDu()) + " VNĐ");
                        etContent.setText(taiKhoanNguon.getTenTK() + " chuyen tien");
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
        setupToolbar();

        /*// Lấy mã loại giao dịch
        DbHelper.showProgressDialog(progressBar);

        DbHelper.getTransactionTypeByTransactionTypeCode(ResultCode.CHUYEN_TIEN, new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {

            }

            @Override
            public void onFailureListener(Exception e) {

            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {
                LoaiGiaoDich loaiGiaoDich = snapshot.getValue(LoaiGiaoDich.class);
                maLoaiGGKey = loaiGiaoDich.getKey();
                DbHelper.dismissProgressDialog();
            }
        });


        Log.d("firebase", "abc");*/


        Intent getDataIntent = getIntent();
        flag = (int) getDataIntent.getSerializableExtra("flag");
        if (flag == BeneficiaryManagementActivity.USER_NAME) {
            thuHuong = (ThuHuong) getDataIntent.getSerializableExtra("tkthuhuong");
            long tkThuHuongLong = thuHuong.getTKThuHuong(); // Lưu giá trị long
            String tkThuHuongStr = Long.toString(tkThuHuongLong); // Chuyển đổi thành chuỗi khi cần hiển thị
            etAccountBeneficiary.setText(tkThuHuongStr);
        }
        taiKhoanNguon = new TaiKhoanLienKet();
        taiKhoanHuong = new TaiKhoanLienKet();

        getIntentFromQRCode();
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
                    String accountBeneficiaryString = etAccountBeneficiary.getText().toString().trim();
                    /*if(accountBeneficiaryString.isEmpty()){
                        BuildAlertDialog("Không được để trống người thụ hưởng");
                        tvNameBeneficiary.setText("");
                    }*/

                    // kiểm tra edit text rỗng?
                    if (!accountBeneficiaryString.isEmpty()) {
                        long accountBeneficiary = Long.parseLong(etAccountBeneficiary.getText().toString().trim());
                        if(accountBeneficiary == taiKhoanNguon.getSoTaiKhoan()){
                            BuildAlertDialog("Không thể tự chuyển khoản cho bản thân");
                            tvNameBeneficiary.setText("");
                        }
                        else {
                            // truy vấn đến TaiKhoanLK theo số tài khoản
                            Log.d(String.valueOf(accountBeneficiary), "onFocusChange: ");
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
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                }
            }
        });

        // xử lý ấn "Tiếp tục"
        btTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkvalid = 0;
                // kiểm tra tổng
                String moneyString = etMoney.getText().toString().trim();
                String accountBeneficiaryString = etAccountBeneficiary.getText().toString().trim();
                if (taiKhoanNguonKey.isEmpty()) {
                    BuildAlertDialog("Vui lòng chọn tài khoản nguồn");
                    checkvalid ++;
                } else if (accountBeneficiaryString.isEmpty()) {
                    BuildAlertDialog("Vui lòng nhập tài khoản hưởng");
                    checkvalid ++;
                } else if (moneyString.isEmpty()) { // rỗng
                    BuildAlertDialog("Vui lòng nhập số tiền cần chuyển");
                    checkvalid ++;
                } else if(Double.parseDouble(moneyString) > taiKhoanNguon.getSoDu()){
                    BuildAlertDialog("Không đủ tiền để gd");
                    checkvalid++;
                }else if (GetDate() != taiKhoanNguon.getNgayGD()) {
                    taiKhoanNguon.setNgayGD(GetDate());
                    taiKhoanNguon.setTienDaGD(0);
                } else if (Double.parseDouble(moneyString) + taiKhoanNguon.getTienDaGD() >taiKhoanNguon.getHanMucTK()) {
                    BuildAlertDialog("Số tiền giao dịch vuợt quá hạn mức");
                    checkvalid ++;
                }
                Log.d(String.valueOf(checkvalid), "checkvalid: ");
                if(checkvalid == 0){ // không rỗng
                    double money = Double.parseDouble(moneyString);
                    // kiểm tra số tiền phải >= 1k
                    if (money >= 1000) {
                        transferMoney(money, etContent.getText().toString().trim());
                    } else {
                        toastMessage("Nghèooooooooooooooooooooooooooooo!");
                    }
                }
            }
        });

        ivBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<ThuHuong> options =
                        new FirebaseRecyclerOptions.Builder<ThuHuong>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("ThuHuong"), ThuHuong.class)
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
        tbToolbar.setTitle("Chuyển tiền");
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

    // chuyển tiền
    private void transferMoney(double money, String noiDungChuyenKhoan) {
        DbHelper.updateSurplus(taiKhoanNguonKey, taiKhoanNguon.getSoDu() - money); // tài khoản nguồn
        DbHelper.updateSurplus(taiKhoanHuongKey, taiKhoanHuong.getSoDu() + money); // tài khoản hưởng
        DbHelper.addTransactionHistory(taiKhoanNguon, taiKhoanHuong, money, noiDungChuyenKhoan,"-AWFo21aLu3212YNBUgf");
        BuildAlertDialogSuccess();
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

    public void BuildAlertDialogSuccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chuyển tiền thành công");
        builder.setMessage("Bấm ok để về trang chủ");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TransferMoneyActivity.this, MainActivity.class);
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

    public void getIntentFromQRCode(){
        Intent intent = getIntent();
        String qrCodeData = intent.getStringExtra("SoTaiKhoan");
        long amount = intent.getLongExtra("amount", 0);
        String message = intent.getStringExtra("message");
        etAccountBeneficiary.setText(qrCodeData);
        etContent.setText(message);
        etMoney.setText(String.valueOf(amount));
    }
}