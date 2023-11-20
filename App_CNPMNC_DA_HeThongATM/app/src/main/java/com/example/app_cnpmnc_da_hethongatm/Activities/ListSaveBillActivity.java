package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ListBillAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Model.MauChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSaveBillActivity extends AppCompatActivity {
    private RecyclerView rv_listsavebill;
    private Config config;
    private ListBillAdapter listBillAdapter;
    List<MauChuyenTien> mauChuyenTiens;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsavebill);
        config = new Config(ListSaveBillActivity.this);
        InitUI();
        RenderLayOut();
        GetListBill();
    }
    private void InitUI(){
        rv_listsavebill= findViewById(R.id.rv_listsavebill);
    }
    private void RenderLayOut(){
        rv_listsavebill.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));;
        mauChuyenTiens = new ArrayList<>();
        listBillAdapter = new ListBillAdapter(mauChuyenTiens);
        rv_listsavebill.setAdapter(listBillAdapter);
    }
    private void GetListBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("MauChuyenTien");
        Query query = myRef.orderByChild("MaKHKey").equalTo(config.getCustomerKey());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MauChuyenTien> mau1 = new ArrayList<>();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        MauChuyenTien mauChuyenTien = dataSnapshot.getValue(MauChuyenTien.class);
                        mau1.add(mauChuyenTien);
                    }
                }
                if(!mauChuyenTiens.equals(mau1)){
                    mauChuyenTiens.clear();
                    mauChuyenTiens.addAll(mau1);
                    listBillAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
