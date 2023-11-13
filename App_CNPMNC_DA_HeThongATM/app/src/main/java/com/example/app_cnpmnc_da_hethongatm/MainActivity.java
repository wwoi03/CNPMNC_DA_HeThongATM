package com.example.app_cnpmnc_da_hethongatm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Activities.AccountCardActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.AccountSettingsActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.BeneficiaryManagementActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.ListSaveBillActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.LoginActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.SearchServiceFunctionActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Extend.Config;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;

//import com.example.app_cnpmnc_da_hethongatm.Activities.UnlockCardActivity;

import com.example.app_cnpmnc_da_hethongatm.Fragment.HomeFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.QuickAccessFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // BNV
    BottomNavigationView bnvMenu;

    // Nav menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    // Fragment
    HomeFragment homeFragment;
    TransactionFragment transactionFragment;
    QuickAccessFragment quickAccessFragment;
    Config config;
    // View
    TextView tv_username, tvPhone;
    RelativeLayout ctFastAccess;
    LinearLayout closeFastAccess, llScanQR, llCreateQR, llSaving, llTransferMoney;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config = new Config(this);
        initUI();
        initData(savedInstanceState);
        initListener();
    }

    // Ánh xạ View
    private void initUI() {
        bnvMenu = findViewById(R.id.bnvMenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        ctFastAccess = findViewById(R.id.ctFastAccess);
        closeFastAccess = findViewById(R.id.closeFastAccess);
        llScanQR = findViewById(R.id.llScanQR);
        llCreateQR = findViewById(R.id.llCreateQR);
        llSaving = findViewById(R.id.llSaving);
        llTransferMoney = findViewById(R.id.llTransferMoney);
    }

    // Khởi tạo
    private void initData(Bundle savedInstanceState) {
        config = new Config(MainActivity.this);

        // Fragment
        homeFragment = new HomeFragment();
        transactionFragment = new TransactionFragment();
        quickAccessFragment = new QuickAccessFragment();

        // load giao diện mặc định
        replaceFragment(homeFragment);

        // nav menu
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sacombank");
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        showInfoAccount();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_quanli);
        }*/
    }

    // Xử lý sự kiện
    private void initListener() {
        // xử lý sự kiện bấm vào từng item trên BNV
        bnvMenu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnuHome:
                    replaceFragment(homeFragment);
                    break;
                case R.id.mnuTransaction:
                    replaceFragment(transactionFragment);
                    break;
                case R.id.mnuQuickAccess:
                    ctFastAccess.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);
                    bnvMenu.setVisibility(View.GONE);
                    break;
            }
            return true;
        });

        // xử lý tắt truy cập nhanh
        closeFastAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctFastAccess.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                bnvMenu.setVisibility(View.VISIBLE);
            }
        });

        // xử lý bấm các nút bấm ở truy cập nhanh
        llCreateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage("GenerateQRCodeActivity");
            }
        });

        llScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage("ScanQRActivity");
            }
        });

        llTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage("TransferMoneyActivity");
            }
        });

        llSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage("GenerateQRCodeActivity");
            }
        });
    }

    // chuyển trang
    private void switchPage(String namePage) {
        String className = "com.example.app_cnpmnc_da_hethongatm.Activities." + namePage;

        try {
            Class<?> myClass = Class.forName(className);
            Intent intent = new Intent(this, myClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // load Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_accountSettings:
                Intent intent = new Intent(this, AccountSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_quanli:
                Intent intent2 = new Intent(this, BeneficiaryManagementActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_khoathe:
                /*Intent intent3 = new Intent(this, LockCardActivity.class);
                startActivity(intent3);*/
                break;
            case R.id.nav_mokhoathe:
                /*startActivity(new Intent(this, UnlockCardActivity.class));*/
            case R.id.nav_logout:
                Log.d(String.valueOf(config.getStateLogin()), "trc khi logout: ");
                config.ClearData();
                Log.d(String.valueOf(config.getStateLogin()), "sau khi logout: ");
                Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
//                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent3);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // khởi tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.header_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // tương tác với các item trong menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSearch:
                Intent cart = new Intent(MainActivity.this, ListSaveBillActivity.class);
                startActivity(cart);
                break;
            case R.id.mnuNotification:
                Intent intent1 = new Intent(MainActivity.this, AccountCardActivity.class);
                intent1.putExtra("flag", 10);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        config.ClearData();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // hiển thị thông tin tài khoản
    private void showInfoAccount() {
        View headerView = navigationView.getHeaderView(0);
        tv_username = headerView.findViewById(R.id.tv_username);
        tvPhone = headerView.findViewById(R.id.tvPhone);

        tv_username.setText(config.getCustomerName());
        tvPhone.setText(config.getCustomerPhone());
    }
}