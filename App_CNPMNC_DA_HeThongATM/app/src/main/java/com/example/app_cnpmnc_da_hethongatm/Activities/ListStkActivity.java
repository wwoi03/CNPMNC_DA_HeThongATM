package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.listStkAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Model.TransferMoney;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListStkActivity extends AppCompatActivity {
    private RecyclerView rv_listStk;
    private listStkAdapter stkAdapter;
    private List<TransferMoney> transferMonies;
    private Config config;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststk);
        initUI();
        getListStkFromFirebase();
    }
    private void initUI(){
        config = new Config(this);

        rv_listStk = findViewById(R.id.rv_listStk);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listStk.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv_listStk.addItemDecoration(dividerItemDecoration);
        transferMonies = new ArrayList<>();
        stkAdapter = new listStkAdapter(transferMonies);

        rv_listStk.setAdapter(stkAdapter);
    }

    //Hàm getFullListSTK theo mã tài khoan
    private void getListStkFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoanLienKet");
        DatabaseReference myRefKH = database.getReference("KhachHang");

        /*String customerPhone = config.getCustomerPhone();

        Query query = myRef.orderByChild("Sdt").equalTo(customerPhone);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String makh = snapshot.getKey().toString();

                        if (makh != null) {
                            Query query1 = database.getReference("TheNganHang").child("makh").equalTo(makh);

                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String theNganHang = snapshot.child("maSoThe").toString();

                                        database.getReference("TaiKhoanLienKet").child("maSoThe").equalTo(theNganHang)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }
                    *//*Query query2 = database.getReference("TaiKhoanLienKet").child("")*//*
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TransferMoney> tempTransferMonies = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu của mỗi đối tượng thỏa mãn điều kiện
                    TransferMoney transferMoney = snapshot.getValue(TransferMoney.class);
//                    Log.d(transferMoney.toString(), "onDataChange: ");
                    if(transferMoney!= null && transferMoney.getMaSoThe() == 1451344235){
                        tempTransferMonies.add(transferMoney);
                    }
                    // Thực hiện xử lý với đối tượng taiKhoan ở đây
                }
                if (!transferMonies.equals(tempTransferMonies)) {
                    transferMonies.clear();
                    transferMonies.addAll(tempTransferMonies);
                    stkAdapter.notifyDataSetChanged();
                }
//                stkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListStkActivity.this,"Get List false",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
