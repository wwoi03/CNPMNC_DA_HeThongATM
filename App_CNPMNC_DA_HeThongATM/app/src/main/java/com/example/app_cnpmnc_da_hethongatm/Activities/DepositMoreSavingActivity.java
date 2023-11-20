package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Adapter.SpinnerDepositAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.SpinnerLaiSuatAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.SpinnerSourceAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DepositMoreSavingActivity extends AppCompatActivity {

    Button SendMoney;
    TextView tvSurplus ;
    EditText numberMoney;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference taikhoanlienketRef,guiTietKiemref,SaveMoney, loaiTaiKhoanRef,loaigiaodichRef,transaction, Laisuatref;
    FirebaseAuth firebaseAuth;
    Spinner spinner, sourceSpinner;
    List<GuiTietKiem> guiTietKiemList;
    GuiTietKiem guiTietKiem;
    List<TaiKhoanLienKet> lienKetList;
    TaiKhoanLienKet taiKhoanLienKet;
    String source,sodu, key,keygiaodich, loaitk,kyhan;
    int currentPosition=-1;
    int currentGTKPosition=-1;
    boolean isDeposit=false;
    boolean isGTK=false;
//ff
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_more_saving);

        initUI();

        firebaseDatabase = FirebaseDatabase.getInstance();
        taikhoanlienketRef= firebaseDatabase.getReference("TaiKhoanLienKet");
        SaveMoney = firebaseDatabase.getReference("GuiTietKiem");
        loaiTaiKhoanRef = firebaseDatabase.getReference("LoaiTaiKhoan");
        loaigiaodichRef = firebaseDatabase.getReference("LoaiGiaoDich");
        transaction=firebaseDatabase.getReference("GiaoDich");
        Laisuatref=firebaseDatabase.getReference("LaiSuat");

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
        loaiTaiKhoanRef.orderByChild("TenLoaiTaiKhoan").equalTo("thanh toán").addValueEventListener(new ValueEventListener() {
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
                        source = dataSnapshot.child("SoTaiKhoan").getValue().toString();
                        sodu = dataSnapshot.child("SoDu").getValue().toString();
                        key = dataSnapshot.getKey();

                        taiKhoanLienKet = new TaiKhoanLienKet(Long.parseLong(source), Double.valueOf(sodu), key);
                        lienKetList.add(taiKhoanLienKet);
                    }
                }
                if(String.valueOf(taiKhoanLienKet.getSoDu())!= null){
                    tvSurplus.setText(String.valueOf(taiKhoanLienKet.getSoDu()));
                    SpinnerSourceAdapter spinnerSourceAdapter = new SpinnerSourceAdapter(lienKetList, DepositMoreSavingActivity.this);
                    sourceSpinner.setAdapter(spinnerSourceAdapter);
                    //lay Spinerdata
                    getSpinerdata();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (currentPosition!=-1&&isDeposit==true){
                    taiKhoanLienKet = lienKetList.get(currentPosition);
                    tvSurplus.setText(String.format("%.0f", taiKhoanLienKet.getSoDu()));
                    sourceSpinner.setSelection(currentPosition);
                    isDeposit=false;
                }else {
                    taiKhoanLienKet = lienKetList.get(position);
                    tvSurplus.setText(String.format("%.0f", taiKhoanLienKet.getSoDu()));
                    currentPosition = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                taiKhoanLienKet = lienKetList.get(0);
                tvSurplus.setText(String.format("%.0f", taiKhoanLienKet.getSoDu()));
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
                String number = numberMoney.getText().toString();
                if(number.isEmpty()){

                    Toast.makeText(DepositMoreSavingActivity.this, "Nhập số tiền vô cu ", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    if (Double.valueOf(number) >taiKhoanLienKet.getSoDu()) {
                        Toast.makeText(DepositMoreSavingActivity.this, "So Du khong du de gui tiet kiem", Toast.LENGTH_SHORT).show();
                    } else if (Double.valueOf(number) < 100000.0) {
                        Toast.makeText(DepositMoreSavingActivity.this, "Mẹ có tiền mà nạp kiểu gì vậy mày", Toast.LENGTH_SHORT).show();
                        numberMoney.setText("");
                    } else {
                        deposit(number);
                    }
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentGTKPosition!=-1&&isGTK==true){
                    guiTietKiem = guiTietKiemList.get(currentGTKPosition);
                    spinner.setSelection(currentGTKPosition);
                    isGTK=false;
                }else {
                    guiTietKiem = guiTietKiemList.get(position);
                    currentGTKPosition=position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void deposit(String number){
        SaveMoney.child(guiTietKiem.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tienGui = snapshot.child("TienGui").getValue().toString();
                Double tienGuiMoi = Double.valueOf(tienGui)+Double.valueOf(numberMoney.getText().toString());
                HashMap<String, Object> GTKNew = new HashMap<>();
                GTKNew.put("TienGui", tienGuiMoi);
                SaveMoney.child(guiTietKiem.getKey()).updateChildren(GTKNew);
                DbHelper.updateSurplus(taiKhoanLienKet.getKey(), taiKhoanLienKet.getSoDu()-Double.valueOf(number));
                numberMoney.setText("");
                Toast.makeText(DepositMoreSavingActivity.this, "đã nộp thêm thành công ", Toast.LENGTH_SHORT).show();
                isDeposit=true;
                isGTK=true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //ánh xạ view
    public void initUI(){
        SendMoney=findViewById(R.id.btSendMoney);
        sourceSpinner = findViewById(R.id.sourceSpinner);
        tvSurplus = findViewById(R.id.tvSurplus);
        //Period=findViewById(R.id.tvPeriod);
        numberMoney=findViewById(R.id.EdtNumberMoney);
        spinner=findViewById(R.id.Spiner);

    }
    //xu li spiner
    public void getSpinerdata(){
        guiTietKiemref=firebaseDatabase.getReference("GuiTietKiem");
        guiTietKiemref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                guiTietKiemList = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String tkNguon = dataSnapshot.child("TaiKhoanNguon").getValue().toString();
                    int positionGTK = sourceSpinner.getSelectedItemPosition();
                    String tkNguonSoSanh = String.valueOf(lienKetList.get(positionGTK).getSoTaiKhoan());
                    if (tkNguonSoSanh.equals(tkNguon)) {
                        String GTKkey = dataSnapshot.getKey();
                        String GTKTKTK = dataSnapshot.child("TaiKhoanTietKiem").getValue().toString();
                        guiTietKiem = new GuiTietKiem(GTKkey, Long.valueOf(GTKTKTK));
                        guiTietKiemList.add(guiTietKiem);
                    }

                }
                SpinnerDepositAdapter spinnerDepositAdapter = new SpinnerDepositAdapter(guiTietKiemList, DepositMoreSavingActivity.this);
                spinner.setAdapter(spinnerDepositAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //lay loai tai khoan
    @Override
    protected void onStart() {
        super.onStart();


    }
}