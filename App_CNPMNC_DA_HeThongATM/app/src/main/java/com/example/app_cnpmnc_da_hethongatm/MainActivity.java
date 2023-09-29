package com.example.app_cnpmnc_da_hethongatm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Activities.BeneficiaryManagementActivity;
import com.example.app_cnpmnc_da_hethongatm.Fragment.HomeFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.QuickAccessFragment;
import com.example.app_cnpmnc_da_hethongatm.Fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tvAccountBalance;
    private Button btnGetBalance;
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
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        initData(savedInstanceState);
        initListener();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm ...");
        // Xử lý sự kiện khi submit tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Xử lý tìm kiếm ở đây
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    private void performSearch(String query) {
        // xử lý tìm kiếm ở đây
        //ví dụ : mở một trang web liên quan đến Sacombank
    }


    // Ánh xạ View
    private void initUI() {
        bnvMenu = findViewById(R.id.bnvMenu);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    // Khởi tạo
    private void initData(Bundle savedInstanceState) {
        // Fragment
        homeFragment = new HomeFragment();
        transactionFragment = new TransactionFragment();
        quickAccessFragment = new QuickAccessFragment();

        // load giao diện mặc định
        replaceFragment(homeFragment);

        // nav menu
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_quanli);
        }
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
            case R.id.nav_quanli:
                Intent intent = new Intent(this, BeneficiaryManagementActivity.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}