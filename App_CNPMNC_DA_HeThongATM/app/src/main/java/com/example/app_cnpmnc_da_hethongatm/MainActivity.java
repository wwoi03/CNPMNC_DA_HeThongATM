package com.example.app_cnpmnc_da_hethongatm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app_cnpmnc_da_hethongatm.Fragment.HomeFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.QuickAccessFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnvMenu;
    HomeFragment homeFragment;
    TransactionFragment transactionFragment;
    QuickAccessFragment quickAccessFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();
        initListener();

    }

    // Ánh xạ View
    private void initUI() {
        bnvMenu = findViewById(R.id.bnvMenu);
    }

    // Khởi tạo
    private void initData() {
        homeFragment = new HomeFragment();
        transactionFragment = new TransactionFragment();
        quickAccessFragment = new QuickAccessFragment();

        // load giao diện mặc định
        loadFragment(homeFragment);
        getSupportActionBar().setTitle("Trang chủ");
    }

    // Xử lý sự kiện
    private void initListener() {
        // Xử lý tương tác với các item trong bottom navigation view
        bnvMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuHome:
                        getSupportActionBar().setTitle("Trang chủ");
                        loadFragment(homeFragment);
                        break;

                    case R.id.mnuQuickAccess:
                        getSupportActionBar().setTitle("Tính năng nhanh");
                        loadFragment(quickAccessFragment);
                        break;

                    case R.id.mnuTransaction:
                        getSupportActionBar().setTitle("Giao dịch");
                        loadFragment(transactionFragment);
                        break;
                }
                return true;
            }
        });
    }

    // load Fragment
    public void loadFragment(Fragment fragment) {
        // giúp thế Fragment vào FrameLayout trên giao diện
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }
}