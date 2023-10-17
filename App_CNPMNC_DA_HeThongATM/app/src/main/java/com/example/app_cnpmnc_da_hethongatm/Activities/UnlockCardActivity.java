package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UnlockCardActivity extends AppCompatActivity {

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
    int demso=0;
    int preMaLoai=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_card);

        inits();
        ReadLoaiThe();


    }

    public void inits(){
        spLoaiThe=findViewById(R.id.spLoaiThe);
        spSoThe=findViewById(R.id.spSoThe);
        btKhoaThe=findViewById(R.id.btKhoaThe);
        config=new Config(UnlockCardActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference();
        getCustomerID(config.getCustomerKey());
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
                ArrayAdapter<String> cardtypelist=new ArrayAdapter<String>(UnlockCardActivity.this, android.R.layout.simple_spinner_item,listtype);
                cardtypelist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLoaiThe.setAdapter(cardtypelist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spLoaiThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l ) {
                maloai=listtypeID[i];
                tenloai=listtype[i];
                Check(preMaLoai);
                preMaLoai=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void ReadMaThe(){
        databaseReference.child("TheNganHang")
                .orderByChild("MaKH").equalTo(config.getCustomerKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            long ttt = (long) snap.child("TinhTrangThe").getValue();
                            String lt=String.valueOf(snap.child("LoaiThe").getValue());
                            if (ttt != 0 && lt.equals(maloai))
                                count++;
                        }
                        cardID = new String[count];
                        Idkey = new String[count];
                        int i = 0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            long ttt = (long) snap.child("TinhTrangThe").getValue();
                            String lt=String.valueOf(snap.child("LoaiThe").getValue());
                            if (ttt != 0 && lt.equals(maloai)) {
                                TheNganHang theNganHang = snap.getValue(TheNganHang.class);
                                if (theNganHang != null) {
                                    cardID[i] = String.valueOf(theNganHang.getMaSoThe());
                                    Idkey[i] = snap.getKey();
                                    i++;
                                }
                            }
                        }
                        ArrayAdapter<String> cardlist = new ArrayAdapter<String>(UnlockCardActivity.this, android.R.layout.simple_spinner_item, cardID);
                        cardlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSoThe.setAdapter(cardlist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UnlockCardActivity.this, "Failed ui", Toast.LENGTH_LONG).show();
                    }
                });
        spSoThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                mathe=cardID[position];
                Id=Idkey[position];
                btKhoaThe.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(UnlockCardActivity.this, ConfirmLockCardActivity.class);
                        //Du lieu can day
                        intent.putExtra("mathe",mathe);
                        intent.putExtra("loaithe",tenloai);
                        intent.putExtra("ID",Id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Check(int pre){
        databaseReference.child("TheNganHang")
                .orderByChild("MaKH").equalTo(config.getCustomerKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            TheNganHang theNganHang=snap.getValue(TheNganHang.class);
                            if (theNganHang.getTinhTrangThe()!=0 && theNganHang.getLoaiThe().equals(maloai))
                                demso++;
                        }
                        Log.d("TAG", "onDataChange: dddddddddd"+demso+maloai);
                        if(demso>0){
                            ReadMaThe();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UnlockCardActivity.this);

                            // Thiết lập tiêu đề và nội dung của thông báo
                            builder.setTitle("Bạn không có thẻ " + tenloai + " nào cần mở khóa!")
                                    .setMessage("Vui lòng kiểm tra trước khi khóa. Nhé!");

                            // Thiết lập nút tích cực
                            builder.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if(pre!=-1&&cardID!=null){
                                        maloai=listtypeID[pre];
                                        spLoaiThe.setSelection(pre);
                                    }
                                }
                            });
                            builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(UnlockCardActivity.this, MainActivity.class));
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UnlockCardActivity.this, "Failed ui", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public String customerID;
    public String getCustomerID(String customerKey){
        databaseReference.child("KhachHang").child(customerKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerID= snapshot.child("CCCD").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return customerID;
    }

}