package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class GetOTPActivity extends AppCompatActivity {
    EditText input_phone;
    Button btn_getotp;
    ProgressBar wait_otp;
    boolean flag = true;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        InitUI();
        GetOTP();
    }
    private void InitUI(){
        input_phone = findViewById(R.id.input_phone);
        btn_getotp = findViewById(R.id.btn_getotp);
        wait_otp=findViewById(R.id.wait_otp);
    }
    private void GetOTP(){
        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input_phone.getText().toString().trim().isEmpty()){
                    if(input_phone.getText().toString().trim().length() == 9){
                        flag = false;
                        CheckInputnumber();
                        Log.d("check sdt trung", "onClick: "+flag);
                        wait_otp.setVisibility(View.VISIBLE);
                        btn_getotp.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(flag == true){
                                    Intent intent = new Intent(getApplicationContext(),formUserRegister.class);
                                    intent.putExtra("mobile","0"+input_phone.getText().toString());
                                    startActivity(intent);
                                }
                                else {
                                    wait_otp.setVisibility(View.INVISIBLE);
                                    btn_getotp.setVisibility(View.VISIBLE);
                                }
                            }
                        },5000);
                    }
                    else {
                        Toast.makeText(GetOTPActivity.this,"Vui lòng nhập đúng định dạng sdt",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(GetOTPActivity.this,"Vui lòng nhập đúng định dạng sdt",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    private boolean CheckInputnumber(){
        flag = false;
        DbHelper.firebaseDatabase.getReference("KhachHang").orderByChild("SoDienThoai").equalTo("0"+input_phone.getText().toString().trim())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(GetOTPActivity.this,"SDT bị trùng",Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                        else {
                            flag = true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return flag;
    }

}
