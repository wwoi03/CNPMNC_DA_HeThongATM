package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.Adapter.SearchFunctionServiceAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SearchServiceFunctionActivity extends AppCompatActivity {
    // View
    RecyclerView rvSearchServiceFunction;

    // Adapter
    SearchFunctionServiceAdapter searchFunctionServiceAdapter;
    FirebaseRecyclerOptions<ChucNang> chucNangFirebaseRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service_function);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    public void initUI() {
        rvSearchServiceFunction = findViewById(R.id.rvSearchServiceFunction);
    }

    // khởi tạo dữ liệu
    public void initData() {
        chucNangFirebaseRecyclerOptions = DbHelper.getServiceFunctions();
    }

    // xử lý sự kiện
    public void initListener() {

    }
}