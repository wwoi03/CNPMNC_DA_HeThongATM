package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app_cnpmnc_da_hethongatm.Adapter.SearchFunctionServiceAdapter;
import com.example.app_cnpmnc_da_hethongatm.R;

public class SearchServiceFunctionActivity extends AppCompatActivity {
    // View
    RecyclerView rvSearchServiceFunction;

    // Adapter
    SearchFunctionServiceAdapter searchFunctionServiceAdapter;

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

    }

    // xử lý sự kiện
    public void initListener() {

    }
}