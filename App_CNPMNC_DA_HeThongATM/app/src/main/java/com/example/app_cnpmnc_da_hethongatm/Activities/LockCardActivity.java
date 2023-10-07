package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Adapter.spinnerListCardAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.spinnerListCardTypeAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTheNganHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
                ArrayAdapter<String>cardtypelist=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,listtype);
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
                Log.d("TAG", "Thông tin debug"+maloai);
                ReadMaThe();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

<<<<<<< Updated upstream
        btKhoaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

=======
>>>>>>> Stashed changes
            }
        });
    }

    public void ReadMaThe(){
        databaseReference.child("TheNganHang")
                .orderByChild("LoaiThe").equalTo(maloai)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count=0;
                for (DataSnapshot snap : snapshot.getChildren()){
                    long ttt=(long)snap.child("TinhTrangThe").getValue();
                    if(ttt!=1)
                        count++;
                }
                cardID=new String[count];
                Idkey=cardID;
                int i=0;
                for (DataSnapshot snap : snapshot.getChildren()){
                    long ttt=(long)snap.child("TinhTrangThe").getValue();
                    if(ttt!=1){
                        TheNganHang theNganHang = snap.getValue(TheNganHang.class);
                        if(theNganHang!=null){
                            cardID[i]= String.valueOf(theNganHang.getMaSoThe());
                            Idkey[i]=snap.getKey();
                            i++;
                        }
                    }
                }
                ArrayAdapter<String>cardlist=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,cardID);
                cardlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSoThe.setAdapter(cardlist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LockCardActivity.this,"Failed ui",Toast.LENGTH_LONG).show();
            }
        });
<<<<<<< Updated upstream
    }

    public void inits(){

        //Loai theeeeeeeeeeeee
        String[]item={"Tín dụng", "Ghi nợ"};
        ArrayAdapter<String>loaithe=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,item);
        loaithe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiThe.setAdapter(loaithe);
        spLoaiThe.setPrompt("Loại thẻ");

        spLoaiThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //So theeeeee :{}
        cardids=new String[cards.size()];
<<<<<<< HEAD
      for(int i=0;i<cards.size();i++){
            cardids[i]=""+cards.size();
            //cardids[i].valueOf(cards.get(i).getMasothe());
=======
        //So theeeeee :{}
      for(int i=0;i<cards.size();i++){
            //cardids[i]=""+cards.size();
            cardids[i].valueOf(cards.get(i).getMaSoThe());
>>>>>>> dev-sprint1
       }
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,cardids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSoThe.setAdapter(adapter);
        spSoThe.setPrompt("Mã số thẻ");

=======
>>>>>>> Stashed changes
        spSoThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                mathe=cardID[position];
                Id=Idkey[position];
                btKhoaThe.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(LockCardActivity.this,ConfirmLockCardActiviti.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(LockCardActivity.this);

                // Thiết lập tiêu đề và nội dung của thông báo
                builder.setTitle("Bạn không sở hữu thẻ "+tenloai+ " nào!")
                        .setMessage("Vui lòng tạo thẻ trước khi khóa. Nhé!");

                // Thiết lập nút tích cực
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng nhấn nút Đồng ý
                        // Để đóng dialog, không cần phải thêm gì cả vì nó sẽ tự đóng
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }



}