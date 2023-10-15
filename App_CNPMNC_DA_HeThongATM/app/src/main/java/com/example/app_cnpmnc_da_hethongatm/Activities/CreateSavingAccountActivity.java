package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.LaiSuat;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CreateSavingAccountActivity extends AppCompatActivity {
    Button SendMoney;
    TextView Source, tvSurplus ;
    EditText numberMoney;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference taikhoanlienketRef, Laisuatref,SaveMoney, loaiTaiKhoanRef;
    FirebaseAuth firebaseAuth;
    Spinner spinner;
    List<String>ListLaiSuat;
    LaiSuat laiSuat;

    String sodu, source, kyhan, loaitk, hanmucthe, TenTaiKhoan, TienDaGD, TienGD1Lan, TinhTrangTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving_account);

        initUI();

        firebaseDatabase = FirebaseDatabase.getInstance();
        taikhoanlienketRef= firebaseDatabase.getReference("TaiKhoanLienKet");
        SaveMoney = firebaseDatabase.getReference("GuiTietKiem");
        loaiTaiKhoanRef = firebaseDatabase.getReference("LoaiTaiKhoan");
        firebaseAuth = FirebaseAuth.getInstance();
        // truy xuat tai khoan nguon
        Config config = new Config(this);
        String customerkey = config.getCustomerKey();
        DbHelper.getCardNumber(customerkey);
        String masothe = String.valueOf(DbHelper.MY_CARD);

        Toast.makeText(CreateSavingAccountActivity.this,masothe, Toast.LENGTH_SHORT);
        String savemoney=String.valueOf(DbHelper.MY_CARD);
        Query card = taikhoanlienketRef.orderByChild("MaSoThe").equalTo((masothe));
        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        // Định dạng ngày thành chuỗi ngày/tháng/năm
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayGui = dateFormat.format(currentDate);
        card.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   sodu = dataSnapshot.child("SoDu").getValue().toString();
                   source = dataSnapshot.child("SoTaiKhoan").getValue().toString();
                   hanmucthe = dataSnapshot.child("HanMucThe").getValue().toString();
                   TenTaiKhoan = dataSnapshot.child("TenTaiKhoan").getValue().toString();
                   TienDaGD = dataSnapshot.child("TienDaGD").getValue().toString();
                   TienGD1Lan =  dataSnapshot.child("TienGD1Lan").getValue().toString();
                   TinhTrangTaiKhoan = dataSnapshot.child("TinhTrangTaiKhoan").getValue().toString();
               }
                if(sodu!= null){
                    tvSurplus.setText(sodu);
                    Source.setText(source);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomInt = r.nextInt(100) + 1;

                GuiTietKiem guiTietKiem = new GuiTietKiem(randomInt, Long.valueOf(Source.getText().toString()), ngayGui, kyhan);
                SaveMoney.push().setValue(guiTietKiem);

                TaiKhoanLienKet taiKhoanLienKet = new TaiKhoanLienKet(Double.valueOf(hanmucthe), Long.valueOf(masothe),
                        ngayGui, Double.valueOf(numberMoney.getText().toString()),Long.valueOf(randomInt), TenTaiKhoan, Double.valueOf(TienDaGD),
                        Double.valueOf(TienGD1Lan), Integer.valueOf(TinhTrangTaiKhoan), loaitk);
                taikhoanlienketRef.push().setValue(taiKhoanLienKet);
            }
        });

        //lay Spinerdata
        getSpinerdata();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kyhan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //ánh xạ view
    public void initUI(){
        SendMoney=findViewById(R.id.btSendMoney);
        Source=findViewById(R.id.tvSource);
        tvSurplus = findViewById(R.id.tvSurplus);
        //Period=findViewById(R.id.tvPeriod);
        numberMoney=findViewById(R.id.tvNumberMoney);
        spinner=findViewById(R.id.Spiner);
    }
    //xu li spiner
    public void getSpinerdata(){
       Laisuatref=firebaseDatabase.getReference("LaiSuat");
       Laisuatref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
             ListLaiSuat = new ArrayList<>();
             for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                 String KyHan = dataSnapshot.child("KyHan").getValue().toString();
                 String Tile=dataSnapshot.child("TiLe").getValue().toString();
                 String Spinerdata= KyHan + " - "+ Tile+"%" ;
                 ListLaiSuat.add(Spinerdata);
             }
               ArrayAdapter<String>adapter=new ArrayAdapter<>(CreateSavingAccountActivity.this, android.R.layout.simple_spinner_dropdown_item,ListLaiSuat);
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spinner.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();

        loaiTaiKhoanRef.orderByChild("TenLoaiTaiKhoan").equalTo("Tài khoản tiết kiệm").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    loaitk = dataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}