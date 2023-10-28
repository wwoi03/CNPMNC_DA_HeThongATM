package com.example.app_cnpmnc_da_hethongatm.Activities;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;

import com.google.firebase.database.Query;

import java.util.ArrayList;


public class NewaccountActivity extends AppCompatActivity {
    TextView tvSourceAccount, etsodep;
    Button btsearch;
    ListView listDanhSach;
    ArrayAdapter adapterDanhSach;
    ArrayList arrayDanhSach;

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
        btsearch = findViewById(R.id.btsearch);
        listDanhSach = findViewById(R.id.liststk);

        //khai báo arraylist và gán vào Adapter
        arrayDanhSach = new ArrayList<String>();
        adapterDanhSach = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayDanhSach);
        //gán arrayAdapter vào listView
        listDanhSach.setAdapter(adapterDanhSach);
        //xử lí kiện nút nhấn
        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra số đã nhập chưa
                String msg;
                msg = etsodep.getText().toString();
                if (!etsodep.getText().toString().equals("")) {
                    arrayDanhSach.add(msg);
                    //cập nhật danh sách
                    adapterDanhSach.notifyDataSetChanged();
                    etsodep.setText("");

                }
            }
        });
        //xử lý kiện nhấn để xóa stk
        listDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> agr0, View view, int i, long l) {
                String msg;
                msg = "Bạn vừa xóa số tài khoản:\n";
                msg = msg + arrayDanhSach.get(i);
                //xóa số tài khoản
                arrayDanhSach.remove(i);
                adapterDanhSach.notifyDataSetChanged();
                //hiện thông báo
                Toast.makeText(NewaccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //xử lý chọn số tài khoản
        listDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long l) {
                String msg;
                msg = "Bạn vừa cập nhật số tài khoản mới:\n";
                msg = msg + arrayDanhSach.get(i);
                //hiện thông báo
                Toast.makeText(NewaccountActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    ;
}
