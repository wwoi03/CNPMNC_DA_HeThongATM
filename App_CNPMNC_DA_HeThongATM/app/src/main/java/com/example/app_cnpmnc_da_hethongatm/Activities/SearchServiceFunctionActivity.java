package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cnpmnc_da_hethongatm.Adapter.SearchFunctionServiceAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Extend.UtilityClass;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchServiceFunctionActivity extends AppCompatActivity implements Filterable, SearchFunctionServiceAdapter.Listener {
    // View
    SearchView searchView;
    RecyclerView rvSearchServiceFunction;

    // var
    SearchFunctionServiceAdapter searchFunctionServiceAdapter;
    String tmpSearch = "";
    ArrayList<ChucNang> chucNangs;
    Toolbar tbToolbar;

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
        tbToolbar = findViewById(R.id.tbToolbar);
    }

    // khởi tạo dữ liệu
    public void initData() {
        UtilityClass.setupToolbar(SearchServiceFunctionActivity.this, tbToolbar, "Tìm kiếm chức năng");

        chucNangs = new ArrayList<>();
        searchFunctionServiceAdapter = new SearchFunctionServiceAdapter(SearchServiceFunctionActivity.this, chucNangs);
        getSearchService(chucNangs, searchFunctionServiceAdapter, tmpSearch);
        rvSearchServiceFunction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSearchServiceFunction.setAdapter(searchFunctionServiceAdapter);
    }

    // xử lý sự kiện
    public void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        // Khai báo SearchView và SearchManager
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =  (SearchView) menu.findItem(R.id.mnuSearch).getActionView();

        // Thiết lập SearchManager cho SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Thiết lập biểu tượng tìm kiếm
        searchView.setIconified(true);

        // tự động focus khi bấm vào tìm kiếm
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        // màu sắc
        /*searchView.setBackground(getResources().getDrawable(R.drawable.search_view));*/
        searchView.setQueryHint("Tìm kiếm chức năng...");

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            // tìm kiếm chính xác
            public boolean onQueryTextSubmit(String query) {
                //getFilter().filter(query);
                return false;
            }

            @Override
            // tìm kiếm gần đúng
            public boolean onQueryTextChange(String newText) {
                getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            // Thực hiện công việc tìm kiếm
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString  = constraint.toString().toLowerCase(); // chuyển chữ thường
                tmpSearch = searchString;
                if (searchString.isEmpty()) {
                    chucNangs.clear();
                } else {
                    chucNangs.clear();
                    getSearchService(chucNangs, searchFunctionServiceAdapter, searchString);
                }

                // vì hàm này trả về một đối tượng FilterResults nên ta khởi tạo đối tượng FilterResults filterResults
                FilterResults filterResults = new FilterResults();
                // sau đó gán giá trị list vừa nhận được cho nó thông qua thuộc tính values có trong đối tượng
                filterResults.values = chucNangs;
                return filterResults;
            }

            @Override
            // đổ kết quả tìm kiếm lên giao diện
            protected void publishResults(CharSequence constraint, FilterResults results) {
                chucNangs = (ArrayList<ChucNang>) results.values;
                searchFunctionServiceAdapter.notifyDataSetChanged();
            }
        };
    }

    private void getSearchService(ArrayList<ChucNang> chucNangs, RecyclerView.Adapter adapter, String searchString) {
        DbHelper.firebaseDatabase.getReference("ChucNang")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ChucNang chucNang = dataSnapshot.getValue(ChucNang.class);

                                if (chucNang.getTenChucNang().toLowerCase().contains(searchString)) {
                                    chucNangs.add(chucNang);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // xử lý khi bấm vào một chức năng bất kì
    @Override
    public void setOnClickItemListener(ChucNang serviceFunction) {
        String className = "com.example.app_cnpmnc_da_hethongatm.Activities." + serviceFunction.getMaChucNang();

        try {
            Class<?> myClass = Class.forName(className);
            Intent intent = new Intent(this, myClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // xử lý sự kiện ấn nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Kết thúc Activity hiện tại và quay lại  Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}