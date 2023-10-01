package com.example.app_cnpmnc_da_hethongatm.Extend;

import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DbHelper {

    // Lấy danh sách chức năng
    public static FirebaseRecyclerOptions<ChucNang> getServiceFunctions() {
        FirebaseRecyclerOptions<ChucNang> options = new FirebaseRecyclerOptions.Builder<ChucNang>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ChucNang"), ChucNang.class)
                .build();

        return options;
    }
}
