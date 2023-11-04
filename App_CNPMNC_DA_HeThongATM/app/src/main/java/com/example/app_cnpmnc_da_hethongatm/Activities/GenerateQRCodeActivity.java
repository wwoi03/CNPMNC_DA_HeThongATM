package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {
    private Config config;
    EditText edit_amount, edit_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generateqrcode);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.qr_code);
        edit_amount = findViewById(R.id.edit_amount);
        edit_message = findViewById(R.id.edit_message);
        config = new Config(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference taiKhoanLienKet = FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet");
                taiKhoanLienKet.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String maKhKey = dataSnapshot.child("MaKHKey").getValue(String.class);
                            String customerKey = config.getCustomerKey();
                            if (maKhKey.equals(customerKey)){
                                Long SoTaiKhoan = dataSnapshot.child("SoTaiKhoan").getValue(Long.class);
                                String TenKH = dataSnapshot.child("TenTK").getValue(String.class);
                                EditText editAmount = findViewById(R.id.edit_amount);
                                String amountString = editAmount.getText().toString();
                                long amount = amountString.isEmpty() ? 0 : Long.parseLong(amountString);
                                String message = edit_message.getText().toString();
                                String qrCodeData = SoTaiKhoan + "," + TenKH + "," + amount + "," + message;
                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                try {
                                    BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                    imageView.setImageBitmap(bitmap);
                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}

