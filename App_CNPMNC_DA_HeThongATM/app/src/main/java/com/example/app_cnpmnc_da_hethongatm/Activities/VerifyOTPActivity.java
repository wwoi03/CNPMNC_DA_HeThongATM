package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    EditText otp1,otp2,otp3,otp4,otp5,otp6;
    TextView textmobileshownumber,text_resentOTP;
    Button btn_submit;
    Intent intent;
    String OTP;
    ProgressBar wait_submit;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        InitUI();
        SetText();
        Onsubmit();
        numberMove();
        ResentOTP();
    }


    private void InitUI(){
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        textmobileshownumber = findViewById(R.id.textmobileshownumber);
        btn_submit = findViewById(R.id.btn_submit);
        wait_submit = findViewById(R.id.wait_submit);
        text_resentOTP = findViewById(R.id.text_resentOTP);
    }
    private void SetText(){
        intent = getIntent();
        textmobileshownumber.setText(String.format("+84-%s", intent.getStringExtra("mobile")));
        OTP = intent.getStringExtra("OTP");
    }
    private void Onsubmit(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otp1.getText().toString().trim().isEmpty() &&!otp2.getText().toString().trim().isEmpty() && !otp3.getText().toString().trim().isEmpty() &&!otp4.getText().toString().trim().isEmpty() &&!otp5.getText().toString().trim().isEmpty()&&!otp6.getText().toString().trim().isEmpty()){
                   String enterCode = otp1.getText().toString()
                           +otp2.getText().toString()
                           +otp3.getText().toString()
                           +otp4.getText().toString()
                           +otp5.getText().toString()
                           +otp6.getText().toString();
                   if(OTP !=null){
                       btn_submit.setVisibility(View.INVISIBLE);
                       wait_submit.setVisibility(View.VISIBLE);
                       PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                               OTP,enterCode
                       );
                       FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               btn_submit.setVisibility(View.VISIBLE);
                               wait_submit.setVisibility(View.GONE);
                               if(task.isSuccessful()){
                                   Intent intent1 = new Intent(getApplicationContext(),formUserRegister.class);
                                   Toast.makeText(VerifyOTPActivity.this,"xác thực thành công",Toast.LENGTH_SHORT).show();
                                   intent1.putExtra("MatKhau",enterCode);
                                   intent1.putExtra("Sdt","0"+intent.getStringExtra("mobile"));
                                   Log.d("Mat khau", "onComplete: "+OTP);
                                   Log.d("Sdt", "onComplete: "+"0"+intent.getStringExtra("mobile"));
                                   startActivity(intent1);
                               }
                               else {
                                   Toast.makeText(VerifyOTPActivity.this,"Nhập đúng mã OTP",Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }else {
                       Toast.makeText(VerifyOTPActivity.this,"Kiểm tra interner",Toast.LENGTH_SHORT).show();
                   }
                }
                else {
                    Toast.makeText(VerifyOTPActivity.this,"Vui lòng nhập đủ mã OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void numberMove(){
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void ResentOTP(){
        text_resentOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber("+84"+intent.getStringExtra("mobile"))
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(VerifyOTPActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTPActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                OTP = s;
                                Toast.makeText(VerifyOTPActivity.this,"Đã gửi lại OTP",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
