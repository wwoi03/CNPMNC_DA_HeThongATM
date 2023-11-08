package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;


public class NewaccountActivity extends AppCompatActivity {
    private EditText etTen, etstk;
    private Button btnThem;
    private ListView listDanhSach;
    private ArrayAdapter adapterDanhSach;
    private ArrayList arrayDanhSach;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
        //ánh xạ các đối tượng
        etstk = (EditText) findViewById(R.id.etstk);
        btnThem = (Button) findViewById(R.id.btnThem);
        listDanhSach = (ListView) findViewById(R.id.listTaiKhoan);
        //khai báo ArrayList và gán Adapter
        arrayDanhSach = new ArrayList<String>();
        adapterDanhSach = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayDanhSach);
        //gán ArrayAdapter vào ListView
        listDanhSach.setAdapter(adapterDanhSach);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra tên và stk đã nhập chưa
                String msg;
                msg = "Số tài khoản: " + etstk.getText().toString();
                if( !etstk.getText().toString().equals("")){
                    arrayDanhSach.add(msg);
                    //cập nhật danh sách
                    adapterDanhSach.notifyDataSetChanged();
                    etstk.setText("");
                }
            }

        });

        //xử lý chọn stk
        listDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long l) {
                String msg;
                msg = "Bạn vừa cập nhật số tài khoản mới:\n";
                Intent intent = new Intent(NewaccountActivity.this, AddNewAccount.class);
                intent.putExtra("TaiKhoanSoDep", String.valueOf(arrayDanhSach.get(i)) );
                startActivity(intent);
            }
        });
    }
}