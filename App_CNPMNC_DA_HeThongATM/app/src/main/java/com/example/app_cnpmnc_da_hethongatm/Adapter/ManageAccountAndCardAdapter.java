package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiTaiKhoan;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.ExecutionException;

public class ManageAccountAndCardAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, ManageAccountAndCardAdapter.ManageAccountAndCardAdapterVH> {
    Listener listener;

    public ManageAccountAndCardAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageAccountAndCardAdapterVH holder, int position, @NonNull TaiKhoanLienKet model) {
        holder.tvAccountNumber.setText(String.valueOf(model.getSoTaiKhoan()));

        // lấy loại tài khoản theo key
        DbHelper.getAccountTypeByKey(model.getMaLoaiTKKey(), new DbHelper.FirebaseListener() {
            @Override
            public void onSuccessListener() {

            }

            @Override
            public void onFailureListener(Exception e) {

            }

            @Override
            public void onSuccessListener(DataSnapshot snapshot) {
                LoaiTaiKhoan loaiTaiKhoan = snapshot.getValue(LoaiTaiKhoan.class);
                holder.tvAccountType.setText("Tài khoản " + loaiTaiKhoan.getTenLoaiTaiKhoan());
            }
        });

        initListener(holder, position, model);
    }

    // Xử lý sự kiện
    public void initListener(@NonNull ManageAccountAndCardAdapterVH holder, int position, @NonNull TaiKhoanLienKet model) {
        // Xử lý sự kiện khi bấm vào icon hiển thị số dư
        holder.ivIconSurplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickIconSurplusListener(holder.ivIconSurplus, holder.tvSurplus, model);
            }
        });
    }

    @NonNull
    @Override
    public ManageAccountAndCardAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_slide, parent, false);
        return new ManageAccountAndCardAdapterVH(view);
    }

    class ManageAccountAndCardAdapterVH extends RecyclerView.ViewHolder {
        TextView tvAccountNumber, tvAccountType, tvSurplus;
        ImageView ivIconSurplus;
        LinearLayout llLayoutCard;

        public ManageAccountAndCardAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvAccountType = itemView.findViewById(R.id.tvAccountType);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
            ivIconSurplus = itemView.findViewById(R.id.ivIconSurplus);
            llLayoutCard = itemView.findViewById(R.id.llLayoutCard);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

        listener.adapterOnDataChange();
    }

    public interface Listener {
        void setOnClickIconSurplusListener(ImageView iconSurplus, TextView tvSurplus, TaiKhoanLienKet taiKhoanLienKet);
        void adapterOnDataChange();
    }
}
