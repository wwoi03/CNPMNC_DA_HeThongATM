package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ListAccountSavingsAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class ListAccountSavingsActivity extends AppCompatActivity {

    RecyclerView rc;
    ListAccountSavingsAdapter listAccountSavingsAdapter;

    public static int USER_NAME = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaccountsavings);
        rc = findViewById(R.id.rc_listsavings);

        rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Config config = new Config(this);
        String customerKey = config.getCustomerKey();

        FirebaseRecyclerOptions<GuiTietKiem> options =
                new FirebaseRecyclerOptions.Builder<GuiTietKiem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("GuiTietKiem").orderByChild("MaKHKey").equalTo(customerKey), GuiTietKiem.class)
                        .build();

        listAccountSavingsAdapter = new ListAccountSavingsAdapter(options);
        rc.setAdapter(listAccountSavingsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listAccountSavingsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listAccountSavingsAdapter.stopListening();
    }
}