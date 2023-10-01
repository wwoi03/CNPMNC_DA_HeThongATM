package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ManageAccountAndCardAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, ManageAccountAndCardAdapter.ManageAccountAndCardAdapterVH> {
    Listener listener;

    public ManageAccountAndCardAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ManageAccountAndCardAdapterVH holder, int position, @NonNull TaiKhoanLienKet model) {

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

        public ManageAccountAndCardAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvAccountType = itemView.findViewById(R.id.tvAccountType);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
            ivIconSurplus = itemView.findViewById(R.id.ivIconSurplus);
        }
    }

    public interface Listener {

    }
}
