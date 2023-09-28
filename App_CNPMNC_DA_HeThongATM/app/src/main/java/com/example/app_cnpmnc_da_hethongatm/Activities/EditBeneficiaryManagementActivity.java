package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
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

public class EditBeneficiaryManagementActivity extends AppCompatActivity {
    EditText editTextBeneficiaryName, editTextAccountNumber;
    Button buttonSubmit, buttonBack;

    Beneficiary beneficiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editthuhuong);
        InitUI();
        InitData();

        beneficiary = new Beneficiary();
        String id = beneficiary.getIdThuHuong();
        beneficiary.setIdThuHuong(id);
    }

    public void InitUI(){
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextBeneficiaryName = findViewById(R.id.editTextBeneficiaryName);
        buttonBack = findViewById(R.id.buttonbBack);
        buttonSubmit =  findViewById(R.id.buttonSubmit);
    }

    public void InitData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ThuHuong");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the Beneficiary object from the snapshot
                    Beneficiary beneficiary = snapshot.getValue(Beneficiary.class);

                    // Get the IdThuHuong
                    String id = beneficiary.getIdThuHuong();

                    buttonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("TKThuHuong", editTextAccountNumber.getText().toString());
                            map.put("TenNguoiThuHuong", editTextBeneficiaryName.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("ThuHuong").child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EditBeneficiaryManagementActivity.this, "Đã chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditBeneficiaryManagementActivity.this, "Chỉnh sửa thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
