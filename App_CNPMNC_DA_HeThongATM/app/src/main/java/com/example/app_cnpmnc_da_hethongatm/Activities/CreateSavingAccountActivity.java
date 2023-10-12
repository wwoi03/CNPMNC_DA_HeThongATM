package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.LaiSuat;
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
    DatabaseReference taikhoanlienket, Laisuatref,SaveMoney;
    FirebaseAuth firebaseAuth;
    Spinner spinner;
    List<String>ListLaiSuat;
    LaiSuat laiSuat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving_account);

        initUI();

        firebaseDatabase = FirebaseDatabase.getInstance();
        taikhoanlienket= firebaseDatabase.getReference("TaiKhoanLienKet");
        SaveMoney = firebaseDatabase.getReference("GuiTietKiem");
        firebaseAuth = FirebaseAuth.getInstance();
        // truy xuat tai khoan nguon
        Config config = new Config(this);
        String customerkey = config.getCustomerKey();
        DbHelper.getCardNumber(customerkey);
        String masothe = String.valueOf(DbHelper.MY_CARD);
        String savemoney=String.valueOf(DbHelper.MY_CARD);
        Query card = taikhoanlienket.orderByChild("MaSoThe").equalTo(masothe);
        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        // Định dạng ngày thành chuỗi ngày/tháng/năm
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayGui = dateFormat.format(currentDate);
        card.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stk = snapshot.child("SoTaiKhoan").getValue().toString();
                String sodu =snapshot.child("SoDu").getValue().toString();

                Source.setText(stk);
                tvSurplus.setText(sodu);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        numberMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomInt = r.nextInt(100) + 1;

                GuiTietKiem guiTietKiem = new GuiTietKiem(Long.valueOf(randomInt), Long.valueOf(Source.getText().toString()), ngayGui, Long.valueOf(numberMoney.getText().toString()));
                SaveMoney.push().setValue(guiTietKiem);
            }
        });

        //lay Spinerdata
        getSpinerdata();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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








}