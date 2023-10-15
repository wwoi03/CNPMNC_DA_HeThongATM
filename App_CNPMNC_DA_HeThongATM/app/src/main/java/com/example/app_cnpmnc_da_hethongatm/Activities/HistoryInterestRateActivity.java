package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.HistoryInterestRateAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.LichSuLaiSuat;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryInterestRateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference historyref;

    List<LichSuLaiSuat> lichSuLaiSuatList;
    HistoryInterestRateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_interest_rate);
        init();
        firebaseDatabase = FirebaseDatabase.getInstance();
        historyref=firebaseDatabase.getReference("LichSuLaiSuat");

        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryInterestRateActivity.this, LinearLayoutManager.VERTICAL, false));
        lichSuLaiSuatList = new ArrayList<>();
        historyref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String SoDuTaiKhoanNguon = dataSnapshot.child("SoDuTaiKhoanNguon").getValue().toString();
                    String MaGuiTietKiem = dataSnapshot.child("MaGuiTietKiem").getValue().toString();
                    String SoTienLai = dataSnapshot.child("SoTienLai").getValue().toString();
                    String NgayNhan = dataSnapshot.child("NgayNhan").getValue().toString();
                    LichSuLaiSuat lichSuLaiSuat = new LichSuLaiSuat(MaGuiTietKiem,Long.valueOf(SoTienLai), NgayNhan ,Long.valueOf(SoDuTaiKhoanNguon) );
                    lichSuLaiSuatList.add(lichSuLaiSuat);
                }
                adapter = new HistoryInterestRateAdapter(lichSuLaiSuatList, HistoryInterestRateActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //ánh xạ view
    public  void init(){
        recyclerView=findViewById(R.id.rvHistory);
    }
}