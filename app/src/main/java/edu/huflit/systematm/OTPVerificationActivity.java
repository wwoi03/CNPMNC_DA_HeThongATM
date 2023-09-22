package edu.huflit.systematm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPVerificationActivity extends AppCompatActivity {

    String vertifycationID;
    String Code;
    String phoneNumber;

    TextView txtPhone;
    Button btnVertification;

    EditText edtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        vertifycationID = getIntent().getStringExtra("vertifycationID");
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        txtPhone = findViewById(R.id.txtPhone);
        txtPhone.setText(phoneNumber);
        btnVertification = findViewById(R.id.btnVerification);
        edtCode = findViewById(R.id.edtCode);

        btnVertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Code = edtCode.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vertifycationID,Code);
                signinbyCredentials(credential);
            }
        });

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
                            Toast.makeText(OTPVerificationActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OTPVerificationActivity.this,MainActivity.class));
                        }
                    }
                });
    }
}