package com.example.app_cnpmnc_da_hethongatm.Activities;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import android.content.SharedPreferences;


public class NewaccountActivity extends AppCompatActivity {
    TextView tvSourceAccount, etsodep;
    Button btsearch;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
       // getSupportActionBar().setTitle("Login");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvSourceAccount = findViewById(R.id.tvSourceAccount);
        etsodep = findViewById(R.id.etsodep);
        btsearch =findViewById(R.id.btsearch);
    }


    private View.OnClickListener nhanvaoregister() {
        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(tvSourceAccount.toString(), etsodep.toString())) { // kiểm tra chuỗi có rỗng hay không
                    String email = tvSourceAccount.toString().trim();
                    String password = etsodep.toString().trim();
                    checkAccount(email, password); // kiểm tra tài khoản
                }
            }
        });
        return null;
    }

    private void checkAccount(String email, String password) {
            Query customer = DbHelper.firebaseDatabase.getReference("KhachHang").orderByChild("SoDienThoai").equalTo(email);
    }

    private boolean isEmpty(String tvSourceAccount, String etsodep) {
        if (tvSourceAccount.isEmpty()) {
            toastMessage("Vui lòng nhập số điện thoại");
        } else if (etsodep.isEmpty()) {
            toastMessage("Vui lòng nhập mật khẩu");
        } else {
            return true;
        }
        return false;
    }

    private void toastMessage(String text) {
        Toast.makeText(NewaccountActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
