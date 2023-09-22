package edu.huflit.systematm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText edtPhone, edtCode;
    Button btnSendCode, btnVerify;
    String vertifycationID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwordn);

        mAuth=FirebaseAuth.getInstance();
        edtPhone = findViewById(R.id.edtPhone);
        btnSendCode = findViewById(R.id.btnSendCode);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = edtPhone.getText().toString();
                Sendverificationcode(number);
            }
        });
        edtCode = findViewById(R.id.edtCode);
        btnVerify = findViewById(R.id.btnVerification);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(edtCode.getText().toString()))
                {
                    Toast.makeText(ForgetPasswordActivity.this," Wrong OTP  Entered",Toast.LENGTH_SHORT).show();
                }
                verificationcode(edtCode.getText().toString());
            }
        });
    }

    private void Sendverificationcode(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted( @NonNull PhoneAuthCredential credential)
        {
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s,token);
            vertifycationID = s;
        }
    };

    private void verificationcode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vertifycationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("phone", edtPhone.getText().toString());
                            startActivity(intent);
                        }
                    }
                });
    }
}