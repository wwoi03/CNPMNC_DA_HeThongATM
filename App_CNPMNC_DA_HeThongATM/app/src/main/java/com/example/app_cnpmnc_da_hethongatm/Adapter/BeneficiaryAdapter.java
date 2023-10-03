package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class BeneficiaryAdapter extends FirebaseRecyclerAdapter<ThuHuong, BeneficiaryAdapter.BeneficiaryAdapterVH> {
    Listener listener;

    public BeneficiaryAdapter(@NonNull FirebaseRecyclerOptions<ThuHuong> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull BeneficiaryAdapterVH holder, int position, @NonNull ThuHuong model) {
        holder.tvName.setText(model.getTenNguoiThuHuong());
        holder.tvAccountNumber.setText(String.valueOf(model.getTKThuHuong()));

        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public BeneficiaryAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_beneficiary, parent, false);
        return new BeneficiaryAdapterVH(view);
    }

    // Xử lý sự kiện
    private void initListener(BeneficiaryAdapterVH holder, int position, ThuHuong model) {
        holder.img_Popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnClickPopupListener(view, model, getRef(position));
            }
        });
    }


    class BeneficiaryAdapterVH extends RecyclerView.ViewHolder {
        ImageView ivAccount, img_Popup;
        TextView tvName, tvAccountNumber;

        public BeneficiaryAdapterVH(@NonNull View itemView) {
            super(itemView);

            ivAccount = itemView.findViewById(R.id.ivAccount);
            img_Popup = itemView.findViewById(R.id.img_Popup);
            tvName = itemView.findViewById(R.id.tvName);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);

        }
    }

    public interface Listener {
        void setOnClickPopupListener(View view, ThuHuong thuHuong, DatabaseReference databaseReference);
    }
}
