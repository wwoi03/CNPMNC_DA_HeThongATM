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
                        CheckInputnumber();
                        Log.d("check sdt trung", "onClick: "+flag);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(flag == true){
                                    wait_otp.setVisibility(View.VISIBLE);
                                    btn_getotp.setVisibility(View.INVISIBLE);
                                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mauth)
                                            .setPhoneNumber("+84"+input_phone.getText())
                                            .setTimeout(60L,TimeUnit.SECONDS)
                                            .setActivity(GetOTPActivity.this)
                                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    wait_otp.setVisibility(View.GONE);
                                                    btn_getotp.setVisibility(View.VISIBLE);
                                                    Log.d("onVerificationCompleted", "onVerificationCompleted: ");
                                                }
                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    wait_otp.setVisibility(View.GONE);
                                                    btn_getotp.setVisibility(View.VISIBLE);
                                                    Toast.makeText(GetOTPActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    super.onCodeSent(s, forceResendingToken);
                                                    wait_otp.setVisibility(View.GONE);
                                                    btn_getotp.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(getApplicationContext(),VerifyOTPActivity.class);
                                                    intent.putExtra("mobile",input_phone.getText().toString());
                                                    intent.putExtra("OTP",s);
                                                    Log.d("onCodeSent", "onCodeSent: "+s);
                                                    startActivity(intent);
                                                }
                                            }).build();
                                    PhoneAuthProvider.verifyPhoneNumber(options);
                                }
                            }
                        },8000);
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
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return flag;
    }

}
