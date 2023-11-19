package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ListAccountQRCodeAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.ListBeneficiaryAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class GenerateQRCodeActivity extends AppCompatActivity {
    private Config config;
    EditText edit_amount, edit_message, edit_tkqr;

    ImageView img_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        Button button = findViewById(R.id.btn_backhome);
        ImageView imageView = findViewById(R.id.qr_code);
        edit_amount = findViewById(R.id.edit_amount);
        edit_amount.setCursorVisible(false);
        edit_message = findViewById(R.id.edit_message);
        edit_message.setCursorVisible(false);
        edit_tkqr = findViewById(R.id.edit_list);
        img_select = findViewById(R.id.img_select);
        config = new Config(this);

        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<TaiKhoanLienKet> options =
                        new FirebaseRecyclerOptions.Builder<TaiKhoanLienKet>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet").orderByChild("MaKHKey").equalTo(config.getCustomerKey()), TaiKhoanLienKet.class)
                                .build();

                ListAccountQRCodeAdapter listAccountQRCodeAdapter = new ListAccountQRCodeAdapter(options);

                DialogPlus dialogPlus = DialogPlus.newDialog(GenerateQRCodeActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.activity_list_accountqr))
                        .setExpanded(true, 800)
                        .setOverlayBackgroundResource(android.R.color.transparent) // Đặt màu nền trong suốt
                        .create();

                // Tìm RecyclerView trong layout của DialogPlus
                RecyclerView recyclerView = dialogPlus.getHolderView().findViewById(R.id.rc_listaccountqr);

                // Thiết lập ListAccountQRCodeAdapter cho RecyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(GenerateQRCodeActivity.this));
                recyclerView.setAdapter(listAccountQRCodeAdapter);

                listAccountQRCodeAdapter.startListening();

                // Thiết lập sự kiện click cho mỗi item
                listAccountQRCodeAdapter.setOnItemClickListener(new ListAccountQRCodeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TaiKhoanLienKet model) {
                        long soTaiKhoan = model.getSoTaiKhoan();
                        String stkstring = Long.toString(soTaiKhoan);
                        edit_tkqr.setText(stkstring);
                        dialogPlus.dismiss();// đóng dialogplus
                    }
                });

                dialogPlus.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SoTaiKhoan = edit_tkqr.getText().toString();
                String amountString = edit_amount.getText().toString();
                long amount = amountString.isEmpty() ? 0 : Long.parseLong(amountString);
                String tien = String.valueOf(amount);
                String message = edit_message.getText().toString();
                String qrCodeData = SoTaiKhoan + "," + tien + "," + message;
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
        });
    }
}