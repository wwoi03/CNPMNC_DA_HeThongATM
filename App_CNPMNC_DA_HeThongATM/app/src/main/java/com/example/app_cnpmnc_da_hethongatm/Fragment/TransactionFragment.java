package com.example.app_cnpmnc_da_hethongatm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Adapter.ImageSlideAdapter;
import com.example.app_cnpmnc_da_hethongatm.Adapter.ServiceFuntionAdapter;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.MainActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.ImageSlide;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment implements ServiceFuntionAdapter.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    /*--------------------------- Biến toàn cục ---------------------------*/
    // View
    ViewPager2 vp2Images;
    CircleIndicator3 ci3;
    SearchView svSearchServiceFunction;

    // Adapter
    ImageSlideAdapter imageSlideAdapter;
    ServiceFuntionAdapter serviceFuntionAdapter;

    // RecycleView
    RecyclerView rvServiceFunctions;

    // ArrayList
    ArrayList<ImageSlide> imageSlides;

    // ...
    Handler handler = new Handler();
    Runnable runnable;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initData();
        initListener();

        demoCURDServiceFunction();
    }

    // Ánh xạ view
    private void initUI(View view) {
        vp2Images = view.findViewById(R.id.vp2Images);
        ci3 = view.findViewById(R.id.ci3);
        rvServiceFunctions = view.findViewById(R.id.rvServiceFunctions);
        svSearchServiceFunction = view.findViewById(R.id.svSearchServiceFunction);
    }

    // Khởi tạo
    private void initData() {
        imageSlides = getImageSlides();
        imageSlideAdapter = new ImageSlideAdapter(imageSlides);
        vp2Images.setAdapter(imageSlideAdapter);

        // Liên kết circleIndicator3 với ViewPager2
        ci3.setViewPager(vp2Images);

        // Auto chuyển slide
        runnable = new Runnable() {
            @Override
            public void run() {
                // kiểm tra hết slide
                if (vp2Images.getCurrentItem() == imageSlides.size() - 1)
                    vp2Images.setCurrentItem(0);
                else
                    vp2Images.setCurrentItem(vp2Images.getCurrentItem() + 1);
            }
        };

        // render chức năng
        FirebaseRecyclerOptions<ChucNang> optionsServiceFunctions = DbHelper.getServiceFunctions();
        Log.d("firebase", optionsServiceFunctions.toString());
        serviceFuntionAdapter = new ServiceFuntionAdapter(optionsServiceFunctions, TransactionFragment.this);
        rvServiceFunctions.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvServiceFunctions.setAdapter(serviceFuntionAdapter);

        // tìm kiếm chức năng
        CharSequence query = svSearchServiceFunction.getQuery();
        svSearchServiceFunction.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svSearchServiceFunction.requestFocus();
                }
            }
        });
    }

    // Xử lý sự kiện
    private void initListener() {
        // Xử lý lắng nghe sự kiện thay đổi viewpager2
        vp2Images.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                // khoảng thời giản chuyển slide
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    // lấy danh sách image slide
    private ArrayList<ImageSlide> getImageSlides() {
        ArrayList<ImageSlide> list = new ArrayList<>();

        list.add(new ImageSlide(R.drawable.slide1));
        list.add(new ImageSlide(R.drawable.slide2));
        list.add(new ImageSlide(R.drawable.slide3));

        return list;
    }

    // Xử lý sự kiện khi ấn vào một chức năng bất kỳ
    @Override
    public void setOnClickItemListener(ChucNang serviceFunction, DatabaseReference databaseReference) {
        String className = "com.example.app_cnpmnc_da_hethongatm" + serviceFunction.getMaChucNang();

        try {
            Class<?> myClass = Class.forName(className);
            Intent intent = new Intent(getContext(), myClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        serviceFuntionAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        serviceFuntionAdapter.stopListening();
    }

    public void demoCURDServiceFunction() {
        /*
        * getInstance(): là để có được đối tượng FirebaseDatabase (để tương tác với firebase)
        * DatabaseReference: là một tham chiếu đến một vị trí cụ thể trong cơ sở dữ liệu Firebase
        * DataSnapshot: được sử dụng để đọc dữ liệu từ một DatabaseReference
        * getReference(): là để tạo DatabaseReference
        * child: là để tạo một DatabaseReference mới từ một đối tượng DatabaseReference đã có, trỏ đến một nút con cụ thể.
        * setValue(): được sử dụng để ghi dữ liệu mới vào một nút cơ sở dữ liệu.
        * updateChildren(): để cập nhật một số lượng trường dữ liệu trong một nút mà không làm thay đổi các trường khác
        * */
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference serviceFunctionRef = firebaseDatabase.getReference("ChucNang");

        // Lấy danh sách chức năng: bình thường
        serviceFunctionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                    ChucNang chucNang = DataSnapshot.getValue(ChucNang.class);

                    Log.d("firebase", "Ten: " + chucNang.getTenChucNang());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Lấy danh sách chức năng: dùng Firebase UI
        FirebaseRecyclerOptions<ChucNang> options = new FirebaseRecyclerOptions.Builder<ChucNang>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ChucNang"), ChucNang.class)
                .build();

        // Thêm chức năng
        ChucNang chucNang = new ChucNang("Không cần nhiều lời", "Chỉ cần một câu", "Helllloooo");
        String userId = serviceFunctionRef.push().getKey(); // tạo key cho ChucNang
        serviceFunctionRef.child(userId).setValue(chucNang);

        // Chỉnh sửa chức năng
        Map<String, Object> updates = new HashMap<>();
        updates.put("TenChucNang", "Số 1 VN");

        serviceFunctionRef.child("<nhét_key_vào_đây").updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Lỗi khi cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                });

        // Xóa chức năng
        String keyChucNangDelete = "<nhét_key_vào_đây>";
        serviceFunctionRef.child(keyChucNangDelete).removeValue();
    }
}