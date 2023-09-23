package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText edtPass;
    Button btnRestPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference("User");

        btnRestPass = findViewById(R.id.btnRestPass);
        edtPass = findViewById(R.id.edtPass);

        btnRestPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getIntent().getStringExtra("phone")).child("password").setValue(edtPass.getText().toString());
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
