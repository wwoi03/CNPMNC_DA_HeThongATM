package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.R;

public class PhoneActivity extends AppCompatActivity {
    TextView etNumber;
ImageView btCall;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        etNumber = findViewById(R.id.et_number);
        btCall = findViewById(R.id.bt_call);

        etNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etNumber.getText().toString();
                if (phone.isEmpty()){
                    Toast.makeText(PhoneActivity.this, "Please Enter Number !", Toast.LENGTH_SHORT).show();
                }else {
                    String s = "tell: 0555684445";
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+s));
                    startActivity(intent);
                }
            }
        });
        TextView link1 = findViewById(R.id.link1);
        link1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView link2 = findViewById(R.id.link2);
        link2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}