package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {


    Button btNextt;
    TextView tvSourcet,tvSourcett,etCont, etMont,etDateLimit;

    ImageView ivDateLimitIcon;
    int mYear, mMonth, mDay;

    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        initUI();
        initData();
        initListener();

    }
    @SuppressLint("WrongViewCast")
    public void initUI() {

        config = new Config(AppointmentActivity.this);
        btNextt = findViewById(R.id.btNextt);
        tvSourcet =findViewById(R.id.tvSourcet);
        tvSourcett =findViewById(R.id.tvSourcett);
        etMont = findViewById(R.id.etMont);
        etCont = findViewById(R.id.etCont);
        etDateLimit = findViewById(R.id.etDateLimit);
        ivDateLimitIcon = findViewById(R.id.ivDateLimitIcon);
        etDateLimit = findViewById(R.id.etDateLimit);
        Log.d("TAG", String.valueOf(config.getCustomerKey()));

    }
    private void initData() {
        Intent intent= getIntent();
        Log.d("TAG", String.valueOf(DbHelper.getAcountIDbyCusKey(config.getCustomerKey())));
        tvSourcet.setText(String.valueOf(DbHelper.getAcountIDbyCusKey(config.getCustomerKey())));
        tvSourcett.setText(String.valueOf(DbHelper.getAcountIDbyCusKey(config.getCustomerKey())));
        if(intent!=null&&intent.hasExtra("TaiKhoanSoDep")){
            etMont.setText(intent.getStringExtra("TaiKhoanSoDep"));
            etCont.setText(intent.getStringExtra("TaiKhoanSoDep"));
        }
        btNextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentActivity.this, OseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListener() {
    }
    private void onClickDateLimit() {
        ivDateLimitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dateLimitString = dateFormat(year, month, dayOfMonth);
                                etDateLimit.setText(dateLimitString);
                            }

                            private String dateFormat(int year, int month, int dayOfMonth) {
                                return String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + year;
                            }


                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }




}

