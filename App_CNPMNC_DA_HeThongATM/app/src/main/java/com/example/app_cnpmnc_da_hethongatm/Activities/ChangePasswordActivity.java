package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.R;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldpassword , newpassword,confirmpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //anh xa view
         oldpassword = findViewById(R.id.OldPassword);
         newpassword=findViewById(R.id.NewPassword);
         confirmpass=findViewById(R.id.etConfirmPassword);
    }


    
}