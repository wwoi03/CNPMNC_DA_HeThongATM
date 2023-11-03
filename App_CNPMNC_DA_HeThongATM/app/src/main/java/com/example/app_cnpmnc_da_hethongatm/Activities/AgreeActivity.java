package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;

public class AgreeActivity extends AppCompatActivity {
    Button back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        back = findViewById(R.id.back);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(com.example.app_cnpmnc_da_hethongatm.Activities.AgreeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

