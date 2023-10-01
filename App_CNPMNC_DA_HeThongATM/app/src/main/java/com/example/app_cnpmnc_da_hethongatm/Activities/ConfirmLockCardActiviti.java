/*
package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.Card;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmLockCardActiviti extends AppCompatActivity {
    TextView tvLoaiThe,tvSoThe,tvTinhTrang;
    Button btKhoaThe;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Card card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_lock_card);
        init();
        render();
        listener();
    }

    public void init(){
        tvLoaiThe=findViewById(R.id.tvLoaiThe);
        tvSoThe=findViewById(R.id.tvSoThe);
        tvTinhTrang=findViewById(R.id.tvTinhTrang);
        btKhoaThe=findViewById(R.id.btKhoaThe);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }
    public void render (){
        databaseReference.child("/TheNganHang/1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                card=snapshot.getValue(Card.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tvLoaiThe.setText(card.getLoaithe());
        tvSoThe.setText(card.getMasothe());
        switch (card.getTinhtrangthe()){
            case 0: tvTinhTrang.setText("Hoạt động");break;
            case 1: tvTinhTrang.setText("Thẻ đang khóa");
            case 2: tvTinhTrang.setText("Không thể sử dụng thẻ");
        }
    }

    public void listener(){
        btKhoaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(card.getTinhtrangthe()==0) {
                    card.setTinhtrangthe(1);
                    //Chua lay duoc id nen de dai
                    databaseReference.child("TheNganHang/1/tinhtrangthe").updateChildren(card.toMap(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(ConfirmLockCardActiviti.this, "Sucessed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Toast.makeText(ConfirmLockCardActiviti.this, "Khóa thẻ thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ConfirmLockCardActiviti.this, MainActivity.class));


            }
        });
    }
}*/
