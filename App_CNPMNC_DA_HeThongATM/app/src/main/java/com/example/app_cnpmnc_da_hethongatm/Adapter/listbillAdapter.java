package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.Bill;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class listbillAdapter extends RecyclerView.Adapter<listbillAdapter.listbillHolder>{
    private List<Bill> bills;

    public listbillAdapter(List<Bill> bills) {
        this.bills = bills;
    }

    @NonNull
    @Override
    public listbillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowbill,parent,false);
        return new listbillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listbillHolder holder, int position) {
            Bill bill= bills.get(position);
            if(bill==null){
                return;
            }
            holder.tv_time.setText(bill.getGioGiaoDich()+" "+bill.getNgayGiaoDich());
            holder.tv_tien.setText(String.valueOf(bill.getSoTienGiaoDich()));
            holder.tv_noidung.setText(bill.getNoiDungChuyenKhoan());
    }
    @Override
    public int getItemCount() {
        if(bills!=null){
            return bills.size();
        }
        return 0;
    }

    public class listbillHolder extends RecyclerView.ViewHolder{
        private TextView tv_time,tv_tien,tv_noidung;

        public listbillHolder(@NonNull View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_tien = itemView.findViewById(R.id.tv_tien);
            tv_noidung = itemView.findViewById(R.id.tv_noidung);
        }
    }

}
