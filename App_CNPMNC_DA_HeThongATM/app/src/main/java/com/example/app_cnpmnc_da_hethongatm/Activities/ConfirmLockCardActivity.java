
package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConfirmLockCardActivity extends AppCompatActivity {
    TextView tvLoaiThe,tvSoThe,tvTinhTrang,tvAccountNumber,tvAccountType;
    Button btXacNhan;

    TheNganHang theNganHang;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mathe,tenloai,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_lock_card);
        Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra("mathe")){
            mathe=intent.getStringExtra("mathe");
            tvAccountNumber.setText(mathe);
        }
        if(intent!=null&&intent.hasExtra("loaithe")){
            tenloai=intent.getStringExtra("loaithe");
            tvAccountType.setText(tenloai);
        }
        if(intent!=null&&intent.hasExtra("ID")){
            id=intent.getStringExtra("ID");
        }

        init();
        getData();
        listener();
    }

    public void init(){
        tvAccountNumber=findViewById(R.id.tvAccountNumber);
        tvAccountType=findViewById(R.id.tvAccountType);
        tvLoaiThe=findViewById(R.id.tvLoaiThe);
        tvSoThe=findViewById(R.id.tvSoThe);
        tvTinhTrang=findViewById(R.id.tvTinhTrang);
        btXacNhan=findViewById(R.id.btXacNhan);
        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference();


    }
    public void getData (){
        Log.d("TAG", mathe+tenloai+id);
        databaseReference.child("TheNganHang").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                theNganHang=snapshot.getValue(TheNganHang.class);
                tvLoaiThe.setText(tenloai);
                tvSoThe.setText(mathe);
                switch (theNganHang.getTinhTrangThe()){
                    case 0: tvTinhTrang.setText("Hoạt động");break;
                    case 1: tvTinhTrang.setText("Thẻ đang khóa");break;
                    case 2: tvTinhTrang.setText("Không thể sử dụng thẻ");break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void listener(){
        btXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The hoat dong
                if(theNganHang.getTinhTrangThe()==0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("TinhTrangThe", 1);

                    FirebaseDatabase.getInstance().getReference().child("TheNganHang").child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Khóa thẻ thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(ConfirmLockCardActivity.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Khóa thẻ thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                //Thẻ bị khóa
                else if(theNganHang.getTinhTrangThe()==1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("TinhTrangThe", 0);

                    FirebaseDatabase.getInstance().getReference().child("TheNganHang").child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Mở khóa thẻ thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(ConfirmLockCardActivity.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Mở khóa thẻ thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Thẻ đã bị khóa bởi ngân hàng hoặc gặp sự cố khác", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
