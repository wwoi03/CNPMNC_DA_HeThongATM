package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTheNganHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LockCardActivity extends AppCompatActivity  {


    Spinner spLoaiThe,spSoThe;
    Button btKhoaThe;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String [] listtype;
    String [] listtypeID;
    String maloai;
    String tenloai;
    String[]cardID;
    String mathe;
    String Id;
    String[]Idkey;
    Config config;
    ArrayAdapter<String>cardlist;
    int demso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_card);

        inits();
        ReadLoaiThe();


    }

    public void inits(){
        spLoaiThe=findViewById(R.id.spLoaiThe);
        spSoThe=findViewById(R.id.spSoThe);
        btKhoaThe=findViewById(R.id.btKhoaThe);
        config=new Config(LockCardActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference();

    }


    public void ReadLoaiThe(){

        databaseReference.child("LoaiTheNganHang").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listtype=new String[(int)snapshot.getChildrenCount()];
                listtypeID=new String[(int)snapshot.getChildrenCount()];
                int i=0;
                for(DataSnapshot snap : snapshot.getChildren()){
                    LoaiTheNganHang loaithe=snap.getValue(LoaiTheNganHang.class);
                    listtype[i]=loaithe.getTenTNH();
                    listtypeID[i]=loaithe.getMaLoaiTNH();
                    i++;
                }
                ArrayAdapter<String>cardtypelist=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_dropdown_item,listtype);
                cardtypelist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLoaiThe.setAdapter(cardtypelist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spLoaiThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloai = listtypeID[i];
                tenloai = listtype[i];
                Check();
                Log.d("TAG", "Thông tin debug" + maloai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




            public void ReadMaThe() {
                databaseReference.child("TheNganHang")
                        .orderByChild("MaKH").equalTo(config.getCustomerKey())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int count = 0;
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    long ttt = (long) snap.child("TinhTrangThe").getValue();
                                    String lt=String.valueOf(snap.child("LoaiThe").getValue());
                                    if (ttt != 1 && lt.equals(maloai))
                                        count++;
                                }
                                cardID = new String[count];
                                Idkey = new String[count];
                                int i = 0;
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    long ttt = (long) snap.child("TinhTrangThe").getValue();
                                    String lt=String.valueOf(snap.child("LoaiThe").getValue());
                                    if (ttt != 1 && lt.equals(maloai)) {
                                        TheNganHang theNganHang = snap.getValue(TheNganHang.class);
                                        if (theNganHang != null) {
                                            cardID[i] = String.valueOf(theNganHang.getMaSoThe());
                                            Idkey[i] = snap.getKey();
                                            i++;
                                        }
                                    }
                                }
                                cardlist = new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item, cardID);
                                cardlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spSoThe.setAdapter(cardlist);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LockCardActivity.this, "Failed ui", Toast.LENGTH_LONG).show();
                            }
                        });


                spSoThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                        mathe = cardID[position];
                        Id = Idkey[position];
                        Log.d("Configtgfbgg",String.valueOf(cardID.length));
                            btKhoaThe.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(LockCardActivity.this, ConfirmLockCardActivity.class);
                                    //Du lieu can day
                                    intent.putExtra("mathe", mathe);
                                    intent.putExtra("loaithe", tenloai);
                                    intent.putExtra("ID", Id);
                                    startActivity(intent);
                                }
                            });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

    public void Check(){
        databaseReference.child("TheNganHang")
                .orderByChild("MaKH").equalTo(config.getCustomerKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        demso = 0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            long ttt = (long) snap.child("TinhTrangThe").getValue();
                            String lt=String.valueOf(snap.child("LoaiThe").getValue());
                            if (ttt != 1 && lt.equals(maloai))
                                demso++;
                        }
                        if(demso>0){
                            ReadMaThe();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LockCardActivity.this);

                            // Thiết lập tiêu đề và nội dung của thông báo
                            builder.setTitle("Bạn không có thẻ " + tenloai + "nào để khóa!")
                                    .setMessage("Vui lòng kiểm tra trước khi khóa. Nhé!");

                            // Thiết lập nút tích cực
                            builder.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý khi người dùng nhấn nút Đồng ý
                                    // Để đóng dialog, không cần phải thêm gì cả vì nó sẽ tự đóng
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LockCardActivity.this, "Failed ui", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
