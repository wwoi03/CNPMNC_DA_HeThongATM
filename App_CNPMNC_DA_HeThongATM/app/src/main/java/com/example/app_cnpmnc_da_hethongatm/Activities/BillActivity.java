package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.listbillAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.Bill;
import com.example.app_cnpmnc_da_hethongatm.Model.TransferMoney;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private RecyclerView rc_listbill;
    private listbillAdapter listbillAdapter;
    private List<Bill> bills;
    private TransferMoney transferMoney;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbill);
        initUI();
        laythongtin();
        getListStkFromFirebase();
    }
    private void initUI(){
        rc_listbill = findViewById(R.id.rc_listbill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_listbill.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rc_listbill.addItemDecoration(dividerItemDecoration);
        bills = new ArrayList<>();
        listbillAdapter = new listbillAdapter(bills);
        rc_listbill.setAdapter(listbillAdapter);
    }
    private void getListStkFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LichSuGiaoDich");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Bill> bill= new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu của mỗi đối tượng thỏa mãn điều kiện
                    Bill billtamthoi = snapshot.getValue(Bill.class);
//                    Log.d(transferMoney.toString(), "onDataChange: ");
                    if(billtamthoi!= null){
                        if(transferMoney.getSoTaiKhoan() == billtamthoi.getTaiKhoanNhan() || transferMoney.getSoTaiKhoan() == billtamthoi.getSoTaiKhoan())
                            if(transferMoney.getSoTaiKhoan() == billtamthoi.getSoTaiKhoan()){
                                billtamthoi.setSoTienGiaoDich(billtamthoi.getSoTienGiaoDich()*-1);
                            }
                        bill.add(billtamthoi);
                    }
                    // Thực hiện xử lý với đối tượng taiKhoan ở đây
                }
                if (!bills.equals(bill)) {
                    bills.clear();
                    bills.addAll(bill);
                    listbillAdapter.notifyDataSetChanged();
                }
//                stkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void laythongtin(){
        transferMoney = (TransferMoney) getIntent().getSerializableExtra("transferMoney");
    }
}


