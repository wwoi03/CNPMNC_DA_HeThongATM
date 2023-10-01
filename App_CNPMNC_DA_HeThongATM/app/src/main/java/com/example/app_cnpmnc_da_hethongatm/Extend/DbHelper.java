package com.example.app_cnpmnc_da_hethongatm.Extend;

import androidx.annotation.NonNull;

import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
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

    // Lấy danh sách tài khoản liên kết
    public static FirebaseRecyclerOptions<TaiKhoanLienKet> getAffiliateAccount() {

        String maSoThe = "";

        FirebaseRecyclerOptions<TaiKhoanLienKet> options = new FirebaseRecyclerOptions.Builder<TaiKhoanLienKet>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("TaiKhoanLienKet").child("MaSoThe").equalTo(maSoThe), TaiKhoanLienKet.class)
                .build();

        return options;
    }

    // Lấy khách hàng
    public static KhachHang getCustomer(String phone) {
        KhachHang khachHang = new KhachHang();

        Query query = firebaseDatabase.getReference("KhachHang").child("SoDienThoai").equalTo(phone);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        return khachHang;
    }
}
