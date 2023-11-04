package com.example.app_cnpmnc_da_hethongatm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Activities.AccountSettingsActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.BeneficiaryManagementActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.GenerateQRCodeActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.ListAccountSavingsActivity;

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

    // View
    TextView tv_username, tvPhone;

    // Confix
    Config config;

    public static int USER_NAME = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData(savedInstanceState);
        initListener();
    }

    // Ánh xạ View
    private void initUI() {
        bnvMenu = findViewById(R.id.bnvMenu);
        drawerLayout = findViewById(R.id.drawer_layout);


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
                    replaceFragment(quickAccessFragment);
                    break;
            }
            return true;
        });
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
            case R.id.nav_ruttien:
                Intent intent3 = new Intent(this, ListAccountSavingsActivity.class);
                startActivity(intent3);
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
            case R.id.mnuScan:
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
                intentIntegrator.setBeepEnabled(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            String qrCodeData = intentResult.getContents();
            String[] parts = qrCodeData.split(",");
            String SoTaiKhoan = parts[0];
            long amount = 0;  // Giá trị mặc định
            String message = "";  // Giá trị mặc định
            if (parts.length > 2) {
                amount = Long.parseLong(parts[2]);  // Chuyển đổi amount sang long
            }
            if (parts.length > 3) {
                message = parts[3];
            }
            Intent intent = new Intent(MainActivity.this, TransferMoneyActivity.class);
            intent.putExtra("SoTaiKhoan", SoTaiKhoan);
            intent.putExtra("amount", amount);  // Truyền amount như một long
            intent.putExtra("message", message);
            intent.putExtra("flag", -1 );
            startActivity(intent);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public void onBackPressed() {
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