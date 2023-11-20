package com.example.app_cnpmnc_da_hethongatm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;

import com.example.app_cnpmnc_da_hethongatm.Activities.ApoimentActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.AppointmentActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.AddNewAccountActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.CreateSavingAccountActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.DepositMoreSavingActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.ManageAccountAndCardActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.NewaccountActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.PhoneActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Adapter.ImageSlideAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.ImageSlide;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    // Biến toàn cục
    ViewPager2 vp2Images;
    CircleIndicator3 ci3;
    ImageSlideAdapter imageSlideAdapter;
    ArrayList<ImageSlide> imageSlides;
    Handler handler = new Handler();
    Runnable runnable;
    LinearLayout lninterestrate;
    ImageView imgDeposit;
    LinearLayout cvTransferMoney, cvManageAccountAndCard, cvManageSaving, cvOpenNewAccount;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initData();
        initListener();
    }

    // Ánh xạ view
    private void initUI(View view) {
        vp2Images = view.findViewById(R.id.vp2Images);
        ci3 = view.findViewById(R.id.ci3);
        cvTransferMoney = view.findViewById(R.id.cvTransferMoney);
        cvManageAccountAndCard = view.findViewById(R.id.cvManageAccountAndCard);
        cvManageSaving=view.findViewById(R.id.cvManageSaving);
        cvOpenNewAccount=view.findViewById(R.id.cvOpenNewAccount);
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

        // Xử lý bấm vào chuyển tiền
        cvTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransferMoneyActivity.class);
                intent.putExtra("flag", -1 );
                startActivity(intent);
            }
        });

        // SỔ tiết kiệm
        cvManageSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateSavingAccountActivity.class);
                startActivity(intent);
            }
        });

        // quản lý thẻ và tài khoản
        cvManageAccountAndCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManageAccountAndCardActivity.class);
                startActivity(intent);
            }
        });

        // quản lý thẻ và tài khoản
        cvOpenNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewaccountActivity.class);
                startActivity(intent);
            }
        });
        cvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApoimentActivity.class);
                startActivity(intent);
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
}