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

import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTheNganHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LookCardActivity extends AppCompatActivity {

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
<<<<<<< Updated upstream:App_CNPMNC_DA_HeThongATM/app/src/main/java/com/example/app_cnpmnc_da_hethongatm/Activities/LookCardActivity.java
        setContentView(R.layout.activity_look_card);
=======
        setContentView(R.layout.activity_unlock_card);

        inits();
        ReadLoaiThe();


    }

    public void inits(){
        spLoaiThe=findViewById(R.id.spLoaiThe);
        spSoThe=findViewById(R.id.spSoThe);
        btKhoaThe=findViewById(R.id.btKhoaThe);
        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference();
>>>>>>> Stashed changes:App_CNPMNC_DA_HeThongATM/app/src/main/java/com/example/app_cnpmnc_da_hethongatm/Activities/UnlockCardActivity.java
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
                Log.d("TAG", "Thông tin debug"+maloai);
                ReadMaThe();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                            if(ttt!=0)
                                count++;
                        }
                        cardID=new String[count];
                        Idkey=cardID;
                        int i=0;
                        for (DataSnapshot snap : snapshot.getChildren()){
                            long ttt=(long)snap.child("TinhTrangThe").getValue();
                            if(ttt!=0){
                                TheNganHang theNganHang = snap.getValue(TheNganHang.class);
                                if(theNganHang!=null){
                                    cardID[i]= String.valueOf(theNganHang.getMaSoThe());
                                    Idkey[i]=snap.getKey();
                                    i++;
                                }
                            }
                        }
                        ArrayAdapter<String>cardlist=new ArrayAdapter<String>(UnlockCardActivity.this, android.R.layout.simple_spinner_item,cardID);
                        cardlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSoThe.setAdapter(cardlist);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UnlockCardActivity.this,"Failed ui",Toast.LENGTH_LONG).show();
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
                        Intent intent=new Intent(UnlockCardActivity.this,ConfirmLockCardActiviti.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(UnlockCardActivity.this);

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