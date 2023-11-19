package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WithdrawSavingsActivity extends AppCompatActivity {

    TextView textViewNgayGui, textViewTaiKhoanNguon, textViewTaiKhoanTietKiem, textViewTienGui, textViewTienLaiToiKy;

    Button btn_quaylaisavings, btn_xacnhanrut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruttien);
        initID();
        initData();
        clickprocess();
    }

    public void initID(){
        textViewNgayGui = findViewById(R.id.textViewNgayGui);
        textViewTaiKhoanNguon = findViewById(R.id.textViewTaiKhoanNguon);
        textViewTaiKhoanTietKiem = findViewById(R.id.textViewTaiKhoanTietKiem);
        textViewTienGui = findViewById(R.id.textViewTienGui);
        textViewTienLaiToiKy = findViewById(R.id.textViewTienLaiToiKy);
        btn_quaylaisavings = findViewById(R.id.btn_quaylaisavings);
        btn_xacnhanrut = findViewById(R.id.btn_xacnhanrut);
    }

    public void initData() {
        Intent getIntent = getIntent();

        // Lấy key từ Intent
        String id = getIntent.getStringExtra("Key");

        DatabaseReference guiTietKiemRef = FirebaseDatabase.getInstance().getReference().child("GuiTietKiem");
        guiTietKiemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Lấy key hiện tại
                    String currentKey = dataSnapshot.getKey();

                    // So sánh key hiện tại với key từ Intent
                    if (currentKey.equals(id)) {
                        // Nếu chúng khớp, lấy và hiển thị thông tin

                        Long taikhoantietkiem = dataSnapshot.child("TaiKhoanTietKiem").getValue(long.class);
                        String tktietkiem = String.valueOf(taikhoantietkiem);
                        textViewTaiKhoanTietKiem.setText(tktietkiem);

                        String ngaygui = dataSnapshot.child("NgayGui").getValue(String.class);
                        textViewNgayGui.setText(ngaygui);

                        Long taikhoannguon = dataSnapshot.child("TaiKhoanNguon").getValue(long.class);
                        String tknguon = String.valueOf(taikhoannguon);
                        textViewTaiKhoanNguon.setText(tknguon);

                        Long tiengui = dataSnapshot.child("TienGui").getValue(long.class);
                        String tg = String.valueOf(tiengui);
                        textViewTienGui.setText(tg);

                        Long tienlai = dataSnapshot.child("TienLaiToiKy").getValue(long.class);
                        String tl = String.valueOf(tienlai);
                        textViewTienLaiToiKy.setText(tl);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clickprocess(){
        btn_quaylaisavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_xacnhanrut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = getIntent();
                String id = getIntent.getStringExtra("Key");
                DatabaseReference guiTietKiemRef = FirebaseDatabase.getInstance().getReference().child("GuiTietKiem");
                DatabaseReference taiKhoanLienKetRef = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");

                guiTietKiemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String currentKey = dataSnapshot.getKey();
                            if (currentKey.equals(id)){
                                Double tiengui = dataSnapshot.child("TienGui").getValue(double.class);
                                // Kiểm tra nếu tiengui = 0
                                if (tiengui == 0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setTitle("Không đủ tiền để rút");
                                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    return; // Thoát ra khỏi phương thức
                                }
                                Long taikhoannguon = dataSnapshot.child("TaiKhoanNguon").getValue(Long.class);
                                taiKhoanLienKetRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                            Long sotaikhoan = dataSnapshot1.child("SoTaiKhoan").getValue(Long.class);
                                            Double soDu = dataSnapshot1.child("SoDu").getValue(double.class);
                                            if (taikhoannguon.equals(sotaikhoan)){
                                                double tongso = soDu + tiengui;
                                                dataSnapshot.getRef().child("TienGui").setValue(0);
                                                dataSnapshot1.getRef().child("SoDu").setValue(tongso);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                        Intent intent = new Intent(WithdrawSavingsActivity.this, NotificationActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}
