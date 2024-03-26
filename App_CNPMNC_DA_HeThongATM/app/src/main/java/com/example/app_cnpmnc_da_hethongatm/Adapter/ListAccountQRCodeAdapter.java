package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListAccountQRCodeAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, ListAccountQRCodeAdapter.myViewHolder>{

    private OnItemClickListener listener;

    public ListAccountQRCodeAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull TaiKhoanLienKet model) {

        holder.tv_tkqr.setText(String.valueOf(model.getSoTaiKhoan()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(model);
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accountqr, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tkqr;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tkqr = itemView.findViewById(R.id.tv_tkqr);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaiKhoanLienKet model);
    }

    public void setOnItemClickListener(ListAccountQRCodeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}