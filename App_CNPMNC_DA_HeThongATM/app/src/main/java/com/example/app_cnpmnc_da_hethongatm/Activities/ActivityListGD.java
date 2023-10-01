package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.GiaoDichAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.GiaoDich;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityListGD extends AppCompatActivity {
    RecyclerView rv_listgd;
    GiaoDichAdapter giaoDichAdapter;
    List<GiaoDich> giaoDiches;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_giaodich);
        initUI();
        RenderListGD();
        getDataFormFireBase();
    }

    private void initUI(){
        rv_listgd =findViewById(R.id.rv_listgd);
    }
    private void RenderListGD(){
//        FirebaseRecyclerOptions<GiaoDich> giaoDichFirebaseRecyclerOptions = DbHelper.getGiaoDichOptions(Stk);
//        giaoDichAdapter = new GiaoDichAdapter(giaoDichFirebaseRecyclerOptions);
        rv_listgd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        giaoDiches = new ArrayList<>();
        giaoDichAdapter = new GiaoDichAdapter(giaoDiches);
        rv_listgd.setAdapter(giaoDichAdapter);
    }
    private void getDataFormFireBase(){
        Intent intent = getIntent();
        long stk = intent.getLongExtra("taiKhoanNguon",0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GiaoDich");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<GiaoDich> gd1 = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GiaoDich giaoDich = snapshot.getValue(GiaoDich.class);
                    if(stk == giaoDich.getTaiKhoanNguon() || stk == giaoDich.getTaiKhoanNhan()){
                        if(stk == giaoDich.getTaiKhoanNguon()){
                            giaoDich.setSoTienGiaoDich(giaoDich.getSoTienGiaoDich()*-1);
                        }
                        else if(stk == giaoDich.getTaiKhoanNhan()){
                            giaoDich.setSoDuLucGui(giaoDich.getSoDuLucNhan());
                        }
                        gd1.add(giaoDich);
                    }
                }
                if(!giaoDiches.equals(gd1)){
                    giaoDiches.clear();
                    giaoDiches.addAll(gd1);
                    giaoDichAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
