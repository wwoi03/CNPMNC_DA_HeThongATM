package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Adapter.SpinnerLaiSuatAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.SpinnerSourceAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.GiaoDich;
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
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CreateSavingAccountActivity extends AppCompatActivity {
    Button SendMoney;
    TextView tvSurplus ;
    EditText numberMoney;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference taikhoanlienketRef, Laisuatref,SaveMoney, loaiTaiKhoanRef,loaigiaodichRef,transaction;
    FirebaseAuth firebaseAuth;
    Spinner spinner, sourceSpinner;
    List<LaiSuat> ListLaiSuat;
    List<TaiKhoanLienKet> lienKetList;
    LaiSuat laiSuat;

    String sodu, key,keygiaodich, loaitk,kyhan, tknguon;
    TaiKhoanLienKet taiKhoanLienKet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving_account);

        initUI();

        firebaseDatabase = FirebaseDatabase.getInstance();
        taikhoanlienketRef= firebaseDatabase.getReference("TaiKhoanLienKet");
        SaveMoney = firebaseDatabase.getReference("GuiTietKiem");
        loaiTaiKhoanRef = firebaseDatabase.getReference("LoaiTaiKhoan");
        loaigiaodichRef = firebaseDatabase.getReference("LoaiGiaoDich");
        transaction=firebaseDatabase.getReference("GiaoDich");

        firebaseAuth = FirebaseAuth.getInstance();
        // truy xuat tai khoan nguon
        Config config = new Config(this);
        String customerkey = config.getCustomerKey();


        Query loaigiaodich = loaigiaodichRef.orderByChild("key").equalTo((customerkey));
        Query tklienket = taikhoanlienketRef.orderByChild("MaKHKey").equalTo((customerkey));
        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        //lấy giờ hiện tại
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String gioHienTai = timeFormat.format(currentDate);
        // Định dạng ngày thành chuỗi ngày/tháng/năm
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayGui = dateFormat.format(currentDate);

        Query queryLoaiTK = loaiTaiKhoanRef.orderByChild("TenLoaiTaiKhoan").equalTo("thanh toán");
        queryLoaiTK.addValueEventListener(new ValueEventListener() {
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
        tklienket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lienKetList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String loaitk1 = dataSnapshot.child("MaLoaiTKKey").getValue().toString();
                    if (loaitk1.equals(loaitk)) {
                        tknguon = dataSnapshot.child("SoTaiKhoan").getValue().toString();
                        sodu = dataSnapshot.child("SoDu").getValue().toString();
                        key = dataSnapshot.getKey();

                        TaiKhoanLienKet taiKhoanLienKet = new TaiKhoanLienKet(Long.parseLong(tknguon), Double.valueOf(sodu), key);
                        lienKetList.add(taiKhoanLienKet);
                    }
                }
                if(sodu!= null){
                    tvSurplus.setText(sodu);
                    SpinnerSourceAdapter spinnerSourceAdapter = new SpinnerSourceAdapter(lienKetList, CreateSavingAccountActivity.this);
                    sourceSpinner.setAdapter(spinnerSourceAdapter);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                taiKhoanLienKet = lienKetList.get(position);
                tvSurplus.setText(String.format("%.0f", taiKhoanLienKet.getSoDu()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loaigiaodich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                keygiaodich = snapshot.child("Key").getKey().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID uuidGTK = UUID.randomUUID();
                String guiTietKiemKey = uuidGTK.toString();

                String tkTietKiem =DbHelper.generateUniqueAccountNumber();

                String number = numberMoney.getText().toString();
               if(number.isEmpty()){

                       Toast.makeText(CreateSavingAccountActivity.this, "Nhập số tiền vô cu ", Toast.LENGTH_SHORT).show();
                       return;

               }
               else {
                   if (Double.valueOf(number) > Double.valueOf(sodu)) {
                       Toast.makeText(CreateSavingAccountActivity.this, "So Du khong du de gui tiet kiem", Toast.LENGTH_SHORT).show();

                   } else if ((Double.valueOf(number) <= 3000000.0)) {
                       Toast.makeText(CreateSavingAccountActivity.this, "Mẹ có tiền nạp kiểu gì vậy mày", Toast.LENGTH_SHORT).show();
                       numberMoney.setText("");
                   } else {
                       deposit(guiTietKiemKey, tkTietKiem, number, ngayGui, gioHienTai, customerkey);
                       Toast.makeText(CreateSavingAccountActivity.this, "Thành công rồi đó Chú em ", Toast.LENGTH_SHORT).show();
                       numberMoney.setText("");

                   }
               }

            }
        });

        //lay Spinerdata
        getSpinerdata();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 laiSuat = ListLaiSuat.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void deposit(String guiTietKiemKey, String tkTietKiem, String number, String ngayGui, String gioHienTai, String customerkey){
        UUID uuidGD = UUID.randomUUID();
        String giaoDichKey = uuidGD.toString();

        SaveMoney.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double soDuTKTK=0.0;

                Double tienLaiToiKy = (Double.valueOf(number) * (Double.valueOf(laiSuat.getTiLe()) / 100)) + Double.valueOf(number);

//                           GuiTietKiem guiTietKiem = new GuiTietKiem(guiTietKiemKey,Long.valueOf(tkTietKiem),
//                                   Long.valueOf(Source.getText().toString()),laiSuat.getKey(), ngayGui,tienLaiToiKy,Double.valueOf(number));

                HashMap<String, Object> guiTietKiem = new HashMap<>();
                guiTietKiem.put("Key",guiTietKiemKey);
                guiTietKiem.put("TaiKhoanTietKiem",Long.valueOf(tkTietKiem));
                guiTietKiem.put("TaiKhoanNguon",taiKhoanLienKet.getSoTaiKhoan());
                guiTietKiem.put("LaiSuatKey",laiSuat.getKey());
                guiTietKiem.put("NgayGui",ngayGui);
                guiTietKiem.put("TienLaiToiKy",tienLaiToiKy);
                guiTietKiem.put("TienGui",Double.valueOf(number));
                guiTietKiem.put("MaKHkey",customerkey);

                SaveMoney.child(guiTietKiemKey).setValue(guiTietKiem);

//                           GiaoDich giaoDich = new GiaoDich(giaoDichKey,Long.valueOf(Source.getText().toString()),Long.valueOf(tkTietKiem),
//                                   ngayGui,gioHienTai,"",Double.valueOf(number),0,
//                                   Double.valueOf(sodu)-Double.valueOf(number), soDuTKTK+Double.valueOf(number) ,keygiaodich);
//
                HashMap<String, Object> giaoDich = new HashMap<>();
                giaoDich.put("Key", giaoDichKey);
                giaoDich.put("TaiKhoanNguon", taiKhoanLienKet.getSoTaiKhoan());
                giaoDich.put("TaiKhoanNhan", Long.valueOf(tkTietKiem));
                giaoDich.put("NgayGiaoDich", ngayGui);
                giaoDich.put("GioGiaoDich", gioHienTai);
                giaoDich.put("NoiDungChuyenKhoan", "");
                giaoDich.put("SoTienGiaoDich", Double.valueOf(number));
                giaoDich.put("PhiGiaoDich", 0);
                giaoDich.put("SoDuLucGui",Double.valueOf(sodu)-Double.valueOf(number) );
                giaoDich.put("SoDuLucNhan", soDuTKTK+Double.valueOf(number) );
                giaoDich.put("LoaiGiaoDichKey", keygiaodich);

                transaction.child(giaoDichKey).setValue(giaoDich);
                DbHelper.updateSurplus(key, Double.valueOf(sodu)-Double.valueOf(number));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //ánh xạ view
    public void initUI(){
        SendMoney=findViewById(R.id.btSendMoney);
        tvSurplus = findViewById(R.id.tvSurplus);
        //Period=findViewById(R.id.tvPeriod);
        numberMoney=findViewById(R.id.EdtNumberMoney);
        spinner=findViewById(R.id.Spiner);
        sourceSpinner = findViewById(R.id.sourceSpinner);

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
                    String MaLaiSuat = dataSnapshot.getKey().toString();
                    LaiSuat laiSuat1 = new LaiSuat(MaLaiSuat, KyHan, Double.parseDouble(Tile));
                    ListLaiSuat.add(laiSuat1);
                }
                SpinnerLaiSuatAdapter spinnerLaiSuatAdapter = new SpinnerLaiSuatAdapter(ListLaiSuat, CreateSavingAccountActivity.this);

                 spinner.setAdapter(spinnerLaiSuatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}