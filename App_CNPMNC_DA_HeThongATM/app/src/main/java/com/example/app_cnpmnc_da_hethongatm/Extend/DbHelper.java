package com.example.app_cnpmnc_da_hethongatm.Extend;

import androidx.annotation.NonNull;

import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DbHelper {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    // Lấy danh sách chức năng
    public static FirebaseRecyclerOptions<ChucNang> getServiceFunctions() {
        FirebaseRecyclerOptions<ChucNang> options = new FirebaseRecyclerOptions.Builder<ChucNang>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ChucNang"), ChucNang.class)
                .build();

        return options;
    }

    // Lấy danh sách người thụ hưởng
    public static FirebaseRecyclerOptions<ThuHuong> getBeneficiaries(long maSoThe) {
        FirebaseRecyclerOptions<ThuHuong> options = new FirebaseRecyclerOptions.Builder<ThuHuong>()
                .setQuery(FirebaseDatabase.getInstance().getReference().orderByChild("ThuHuong").equalTo(maSoThe), ThuHuong.class)
                .build();

        return options;
    }
}
