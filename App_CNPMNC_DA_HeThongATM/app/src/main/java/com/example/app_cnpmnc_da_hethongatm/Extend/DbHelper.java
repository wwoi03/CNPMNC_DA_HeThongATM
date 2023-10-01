package com.example.app_cnpmnc_da_hethongatm.Extend;

import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

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

    /*// Lấy thông tin thẻ của khách hàng
    public static TheNganHang getCard(String maKhachHang) {
        Query query = firebaseDatabase.getReference("TheNganHang").child("MaKH").equalTo(maKhachHang);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        TheNganHang theNganHang = new TheNganHang();
        return theNganHang;
    }*/
}
