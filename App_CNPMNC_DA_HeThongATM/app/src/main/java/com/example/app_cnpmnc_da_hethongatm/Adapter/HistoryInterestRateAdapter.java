package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.LichSuLaiSuat;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class HistoryInterestRateAdapter extends RecyclerView.Adapter<HistoryInterestRateAdapter.ViewHolder> {
   List<LichSuLaiSuat> historyList;
   Context context;

    public HistoryInterestRateAdapter(List<LichSuLaiSuat> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historyinterestrate,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSuLaiSuat lichSuLaiSuat = historyList.get(position);
        holder.tvSoDuTaiKhoanNguon.setText(String.valueOf(lichSuLaiSuat.getSoDuTaiKhoanNguon()));
        holder.tvSoTienLai.setText(String.valueOf(lichSuLaiSuat.getSoTienLai()));
        holder.tvNgayNhan.setText(lichSuLaiSuat.getNgayNhan());
        holder.tvMaGuiTietKiem.setText(lichSuLaiSuat.getMaGuiTietKiem());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSoDuTaiKhoanNguon,tvNgayNhan,tvSoTienLai,tvMaGuiTietKiem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaGuiTietKiem = itemView.findViewById(R.id.tvMaGuiTietKiem);
            tvNgayNhan=itemView.findViewById(R.id.tvNgayNhan);
            tvSoTienLai=itemView.findViewById(R.id.tvSoTienLai);
            tvSoDuTaiKhoanNguon=itemView.findViewById(R.id.tvSoDuTaiKhoanNguon);
        }
    }
}
