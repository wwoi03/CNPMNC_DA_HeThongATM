package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Model.Bill;
import com.example.app_cnpmnc_da_hethongatm.Model.TransferMoney;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class TransferMoneyActivity extends AppCompatActivity {

    TextInputEditText input_NguoiThuHuong,input_stk,input_LoiNhan,input_NhapTien;
    TextView tv_stk,tv_sd;
    TransferMoney transferMoney,nguoiThuHuong;
    Button btn_submit;
    int TienGd1Lan;
    String NgayGDHomNay;
    Bill bill = new Bill();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfermoney);
        Init();
        GetIntent();
        ReadNguoiThuHuong();
        XacNhanChuyenTien();
    }

    private void Init(){
        input_NguoiThuHuong = findViewById(R.id.input_NguoiThuHuong);
        input_stk = findViewById(R.id.input_stk);
        tv_stk = findViewById(R.id.tv_stk);
        tv_sd = findViewById(R.id.tv_sd);
        input_LoiNhan = findViewById(R.id.input_LoiNhan);
        input_NhapTien = findViewById(R.id.input_NhapTien);
        btn_submit = findViewById(R.id.btn_submit);
    }
    private void GetIntent(){
        transferMoney = (TransferMoney) getIntent().getSerializableExtra("transferMoney");
        setTextForm();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoanLienKet/"+transferMoney.getKeySTK());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TransferMoney tamthoi = snapshot.getValue(TransferMoney.class);
                if(tamthoi !=null){
                    transferMoney = tamthoi;
                    setTextForm();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setTextForm(){
        tv_stk.setText(String.valueOf(transferMoney.getSoTaiKhoan()));
        tv_sd.setText(String.valueOf(transferMoney.getSoDu()));
        input_LoiNhan.setText(transferMoney.getTenTK()+" Chuyen tien");
    }
    private void XacNhanChuyenTien(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_nguoithuhuong = input_NguoiThuHuong.getText().toString().trim();
                String text_SoTienGD = input_NhapTien.getText().toString().trim();

                if(text_nguoithuhuong.isEmpty() ||text_SoTienGD.isEmpty()){
                    BuildAlertDialog("Vui lòng điền thông tin phù hợp");
                }
                else {
                    GetDataForm();
                    if(TienGd1Lan <10000){
                        BuildAlertDialog("số tiền gửi phải lớn hơn 10k");
                    } else if (TienGd1Lan > transferMoney.getSoDu()) {
                        BuildAlertDialog("Vượt quá số dư hiện tại");
                    } else if(TienGd1Lan > transferMoney.getTienGD1Lan()) {
                        BuildAlertDialog("Số tiền gd lớn nhất là"+String.valueOf(transferMoney.getTienGD1Lan()));
                    } else if (transferMoney.getTienDaGD() > transferMoney.getHanMucThe()) {
                        BuildAlertDialog("Hôm nay đã đạt hạn mức tối đa giao dịch vui lòng quay lại sau");
                    }else if(NgayGDHomNay != transferMoney.getNgayGD()){
                        transferMoney.setNgayGD(NgayGDHomNay);
                        transferMoney.setTienDaGD(0);
                        transferMoney.setSoDu(transferMoney.getSoDu()-TienGd1Lan);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("TaiKhoanLienKet");
                        myRef.child(transferMoney.getKeySTK()).setValue(transferMoney);
                        nguoiThuHuong.setSoDu(nguoiThuHuong.getSoDu()+TienGd1Lan);
                        myRef.child(nguoiThuHuong.getKeySTK()).setValue(nguoiThuHuong);
                        SaveBill();
                        BuildAlertDialogSucess();
                    }
                }
            }
        });
    }


    private void ReadNguoiThuHuong() {
        input_stk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("TaiKhoanLienKet");

                    String stkText = input_stk.getText().toString().trim();
                    if(stkText.isEmpty()){
                        BuildAlertDialog("Không được để trống");
                    }
                    else {
                        long stk = Long.parseLong(stkText);
                        // Sử dụng query để lọc kết quả
                        Query query = myRef.orderByChild("soTaiKhoan").equalTo(stk);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // Do bạn đang truy vấn dựa trên một giá trị duy nhất, nên chỉ cần lấy đối tượng đầu tiên
                                    TransferMoney abc = dataSnapshot.getChildren().iterator().next().getValue(TransferMoney.class);
                                    if(transferMoney.getTenTK() == abc.getTenTK()){
                                        BuildAlertDialog("Không thể tự giao dịch với bản thân");
                                    }
                                    else if (abc != null) {
                                        nguoiThuHuong = abc;
                                        input_NguoiThuHuong.setText(nguoiThuHuong.getTenTK());
                                    } else{
                                        BuildAlertDialog("Không tìm thấy người thụ hưởng");
                                    }
                                } else {
                                    BuildAlertDialog("Không tìm thấy người thụ hưởng");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("FirebaseError", error.getMessage());
                            }
                        });
                    }

                }
            }
        });
    }

    private void BuildAlertDialog(String TenLoi){
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
    private void BuildAlertDialogSucess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chuyển tiền thành công");
        builder.setMessage("Bấm ok để về trang chủ");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TransferMoneyActivity.this,ListStkActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void GetDataForm(){
        TienGd1Lan = Integer.parseInt(input_NhapTien.getText().toString().trim());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        NgayGDHomNay = sdf.format(calendar.getTime());
    }
    private void SaveBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LichSuGiaoDich");
        DatabaseReference newref = myRef.push();
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
//        input_LoiNhan.setText(timeString);
        bill.setGioGiaoDich(timeString);
        bill.setMaGiaoDich(newref.getKey());
        bill.setNgayGiaoDich(NgayGDHomNay);
        bill.setNoiDungChuyenKhoan(input_LoiNhan.getText().toString().trim());
        bill.setSoTaiKhoan(transferMoney.getSoTaiKhoan());
        bill.setSoTienGiaoDich(Integer.parseInt(input_NhapTien.getText().toString().trim()));
        bill.setTaiKhoanNhan(nguoiThuHuong.getSoTaiKhoan());

        newref.setValue(bill);
    }

}
