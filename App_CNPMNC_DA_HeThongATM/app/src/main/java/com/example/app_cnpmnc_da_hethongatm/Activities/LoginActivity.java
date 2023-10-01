package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    EditText phone , Otp, edtPass;
    Button btnLogin;
    TextView btnReset;
    FirebaseAuth mAuth;
    String vertifycationID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference useRef, passRef;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone= findViewById(R.id.edtPhone);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
        mAuth=FirebaseAuth.getInstance();
        btnReset=findViewById(R.id.btnReset);

        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app");
        useRef = firebaseDatabase.getReference("User");
        passRef = firebaseDatabase.getReference("UserPassword");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this," Enter valid Phone No.",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(edtPass.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Nhap password",Toast.LENGTH_SHORT).show();
                } else if (count==5) {
                    Toast.makeText(LoginActivity.this,"GG",Toast.LENGTH_SHORT).show();
                } else
                {
                    passRef.child(phone.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pass = snapshot.child("password").getValue().toString();
                            if (pass.equals(edtPass.getText().toString())){
                                String number = phone.getText().toString();
                                Sendverificationcode(number);
                                savePhoneNumberPref(number);
                                Toast.makeText(LoginActivity.this, "Mk dung", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                count++;
                                Toast.makeText(LoginActivity.this, "Mk sai", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    public void savePhoneNumberPref(String phoneNumber){
        SharedPreferences sharedPreferences = getSharedPreferences("Database", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PhoneNumber", phoneNumber);
        editor.apply();
    }

    private void verificationcode()
    {
        Intent intent = new Intent(LoginActivity.this, OTPVerificationActivity.class);
        intent.putExtra("vertifycationID", vertifycationID);
        intent.putExtra("phoneNumber", phone.getText().toString());
        startActivity(intent);
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
                            Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
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
           /* final String code = credential.getSmsCode();
            if(code!=null)
            {
                verificationcode(code);
                Toast.makeText(LoginActivity.this," Verifycation ok",Toast.LENGTH_SHORT).show();

            }*/
            verificationcode();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginActivity.this," Verifycation Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s,token);
            vertifycationID = s;
            verificationcode();
        }
    };
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dialogView = getLayoutInflater().inflate(R.layout.login_pin_dialog, null);

            builder.setView(dialogView);

            // Get references to dialog elements
            EditText codeEditText = dialogView.findViewById(R.id.edtPin);
            Button submitButton = dialogView.findViewById(R.id.btnSubmit);
            Button cancelButton = dialogView.findViewById(R.id.btnCancel);

            // Create and show the dialog
            final AlertDialog dialog = builder.create();
            dialog.show();

            // Set a listener for the Submit button
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredCode = codeEditText.getText().toString();
                    useRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(enteredCode.equals(snapshot.child("pin").getValue().toString())){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this, "Sai ma pin", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            // Set a listener for the Cancel button
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Close the dialog
                    dialog.dismiss();
                }
            });

        }

    }


}