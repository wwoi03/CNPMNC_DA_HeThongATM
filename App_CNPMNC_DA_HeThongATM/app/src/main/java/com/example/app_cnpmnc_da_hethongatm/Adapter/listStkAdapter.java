package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.TransferMoney;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class listStkAdapter extends RecyclerView.Adapter<listStkAdapter.listStkViewHolder> {

    public List<TransferMoney> transferMonies;

    public listStkAdapter(List<TransferMoney> transferMonies) {
        this.transferMonies = transferMonies;
    }

    @NonNull
    @Override
    public listStkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowstk,parent,false);
        return new listStkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listStkViewHolder holder, int position) {
        TransferMoney transferMoney = transferMonies.get(position);
        if(transferMoney ==null){
            return;
        }
        holder.stk.setText("So Tai Khoan: "+transferMoney.getSoTaiKhoan());
        holder.soDu.setText("So Du: "+transferMoney.getSoDu());
    }

    @Override
    public int getItemCount() {
        if(transferMonies!=null){
            return transferMonies.size();
        }
        return 0;
    }

    public class listStkViewHolder extends RecyclerView.ViewHolder{

        private TextView stk,soDu;

        public listStkViewHolder(@NonNull View itemView) {
            super(itemView);
            stk = itemView.findViewById(R.id.stk);
            soDu = itemView.findViewById(R.id.soDu);
        }
    }
}
