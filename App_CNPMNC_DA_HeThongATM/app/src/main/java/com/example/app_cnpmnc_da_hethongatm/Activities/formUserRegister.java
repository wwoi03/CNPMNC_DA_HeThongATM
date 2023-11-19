package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class formUserRegister extends AppCompatActivity {
    EditText edt_tenkh,edt_email,edt_diachi,edt_cccd,edt_matkhau;
    Button btn_next;
    TextView tv_ngaysinh;
    RadioGroup radio_group;
    RadioButton radioButton;
    boolean CheckDate = false;
    Intent intent;
    KhachHang khachHang ;
    boolean checkcccd = true;
    String sdt ;
    Pattern pattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\]");
    Pattern password = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    // Tạo một Matcher với inputText và kiểm tra xem có ký tự đặc biệt hay không

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formsignup);
        InitUI();
        CheckGenDer();
        CheckCCCD();
        InitData();
        tv_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetBirthDay();
            }
        });
    }
    private void InitUI(){
        edt_tenkh = findViewById(R.id.edt_tenkh);
        edt_email = findViewById(R.id.edt_email);
        edt_diachi = findViewById(R.id.edt_diachi);
        btn_next = findViewById(R.id.btn_next);
        edt_cccd = findViewById(R.id.edt_cccd);
        tv_ngaysinh = findViewById(R.id.tv_ngaysinh);
        radio_group = findViewById(R.id.radio_group);
        edt_matkhau = findViewById(R.id.edt_matkhau);
    }
    private void InitData(){
        intent = getIntent();
        sdt = intent.getStringExtra("mobile");
    }
    private void CheckGenDer(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = radio_group.getCheckedRadioButtonId();
                radioButton = findViewById(i);
                Matcher matcher  = emailPattern.matcher(edt_email.getText().toString().trim());
                if(TextUtils.isEmpty(edt_tenkh.getText()) ||TextUtils.isEmpty(edt_email.getText()) ||TextUtils.isEmpty(edt_cccd.getText())||TextUtils.isEmpty(tv_ngaysinh.getText())||TextUtils.isEmpty(edt_matkhau.getText())){
                    Toast.makeText(formUserRegister.this,"Vui lòng điền đủ các trường",Toast.LENGTH_SHORT).show();
                    return;
                }else if(edt_cccd.getText().toString().trim().length() != 12){
                    Toast.makeText(formUserRegister.this,"CCCD phải đủ 12 số",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (CheckDate == false) {
                    Toast.makeText(formUserRegister.this,"Vui lòng nhập ngày sinh",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!matcher.find()) {
                    Toast.makeText(formUserRegister.this,"Nhập đúng định dạng Email",Toast.LENGTH_SHORT).show();
                    return;
                }else if (checkcccd == false) {
                    Toast.makeText(formUserRegister.this,"Vui lòng nhập cccd khác",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (edt_matkhau.getText().toString().trim().length() <6) {
                    Toast.makeText(formUserRegister.this,"Mật khẩu phải dài hơn 6 kí tự",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String TenKH = edt_tenkh.getText().toString().trim();
                    String email = edt_email.getText().toString().trim();
                    String diachi = edt_diachi.getText().toString().trim();
                    String cccd = edt_cccd.getText().toString().trim();
                    String ngaySinh = tv_ngaysinh.getText().toString().trim();
                    String gioitinh = radioButton.getText().toString().trim();
                    String matkhau = edt_matkhau.getText().toString().trim();
                    khachHang = new KhachHang(cccd,TenKH,ngaySinh,gioitinh,diachi,email,sdt,matkhau);
                    String keyKH= DbHelper.RegisterNewUser(khachHang);
                    DbHelper.NewTaiKhoanLienKet(khachHang,keyKH);
                    Intent intent1 = new Intent(formUserRegister.this,LoginActivity.class);
                    Toast.makeText(formUserRegister.this,"Đăng ký tài khoản thành công",Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                }
            }
        });
    }
    private void CheckCCCD(){
        edt_cccd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    DbHelper.firebaseDatabase.getReference("KhachHang").orderByChild("CCCD").equalTo(edt_cccd.getText().toString().trim())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        checkcccd = false;
                                        Toast.makeText(formUserRegister.this,"CCCD đã tồn tại",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
            }
        });
    }
    private void GetBirthDay(){
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn ngày tháng năm
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        int age = calculateAge(selectedDate);
                        if (age >= 15) {
                            // Xử lý khi người dùng chọn ngày tháng năm hợp lệ
                            String selectedDateStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                            tv_ngaysinh.setText(selectedDateStr);
                            CheckDate = true;
                        } else {
                            // Hiển thị thông báo nếu không đủ tuổi
                            Toast.makeText(formUserRegister.this, "Bạn phải từ 15 tuổi trở lên", Toast.LENGTH_SHORT).show();
                            CheckDate = false;
                        }
                    }
                },
                currentYear, currentMonth, currentDay);

        // Giới hạn ngày tối thiểu là 15 tuổi trở lên
        // Hiển thị dialog
        datePickerDialog.show();
    }
    private int calculateAge(Calendar birthDate) {
        Calendar currentDate = Calendar.getInstance();
        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // Kiểm tra nếu chưa đến sinh nhật trong năm nay
        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

}
