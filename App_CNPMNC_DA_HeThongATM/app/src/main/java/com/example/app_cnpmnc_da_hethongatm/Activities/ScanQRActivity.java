package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQRActivity.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Quét mã tại đây để giao dịch");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
        intentIntegrator.setBeepEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (intentResult != null){
                String qrCodeData = intentResult.getContents();
                String[] parts = qrCodeData.split(",");
                String SoTaiKhoan = parts[0];
                String amount = "";
                String message = "";
                if (parts.length > 1) {
                    amount = parts[1];
                }
                if (parts.length > 2) {
                    message = parts[2];
                }
                Intent intent = new Intent(ScanQRActivity.this, TransferMoneyActivity.class);
                intent.putExtra("SoTaiKhoan", SoTaiKhoan);
                intent.putExtra("amount", amount);
                intent.putExtra("message", message);
                intent.putExtra("flag", ResultCode.SCAN_QR);
                startActivity(intent);
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            finish();
        }
    }
}