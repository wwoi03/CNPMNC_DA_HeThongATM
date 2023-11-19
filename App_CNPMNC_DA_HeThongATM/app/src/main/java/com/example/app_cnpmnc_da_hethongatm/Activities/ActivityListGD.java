package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Collections;
import java.util.List;

public class ActivityListGD extends AppCompatActivity {
    RecyclerView rv_listgd;
    GiaoDichAdapter giaoDichAdapter;
    List<GiaoDich> giaoDiches;
    Toolbar tbToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_giaodich);
        initUI();

        setupToolbar();

        RenderListGD();
        getDataFormFireBase();
    }

    private void initUI(){
        rv_listgd =findViewById(R.id.rv_listgd);
        tbToolbar =findViewById(R.id.tbToolbar);
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
                    Collections.reverse(giaoDiches);
                    giaoDichAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupToolbar() {
        tbToolbar.setTitle("");
        setSupportActionBar(tbToolbar);

        TextView textView = new TextView(this);
        textView.setText("Lịch sử giao dịch");
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(new androidx.appcompat.widget.Toolbar.LayoutParams(androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
        tbToolbar.addView(textView);

        // kích hoạt nút quay lại trên ActionBar
        if (getSupportActionBar() != null) {
            // Đặt màu trắng cho nút quay lại
            final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_chevron_left_24);
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            // Hiển thị nút quay lại
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // xử lý sự kiện ấn nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Kết thúc Activity hiện tại và quay lại  Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
