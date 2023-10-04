package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class AccountCardAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, AccountCardAdapter.AccountCardAdapterVH> {
    Listener listener;

    public AccountCardAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull AccountCardAdapterVH holder, int position, @NonNull TaiKhoanLienKet model) {
        holder.tvAccountNumber.setText(String.valueOf(model.getSoTaiKhoan()));
        holder.tvSurplus.setText(String.valueOf(model.getSoDu()));

        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public AccountCardAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_card, parent, false);
        return new AccountCardAdapterVH(view);
    }

    // xử lý sự kiện
    private void initListener(AccountCardAdapterVH holder, int position, TaiKhoanLienKet model) {
        holder.llAccountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(model, getRef(position));
            }
        });
    }

    class AccountCardAdapterVH extends RecyclerView.ViewHolder {
        TextView tvAccountNumber, tvSurplus;
        LinearLayout llAccountCard;

        public AccountCardAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
            llAccountCard = itemView.findViewById(R.id.llAccountCard);
        }
    }

    public interface Listener {
        void setOnClickItemListener(TaiKhoanLienKet model, DatabaseReference databaseReference);
    }
}
