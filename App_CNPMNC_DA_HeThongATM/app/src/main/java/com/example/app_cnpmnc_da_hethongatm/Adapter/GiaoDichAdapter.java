package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.GiaoDich;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiGiaoDich;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GiaoDichAdapter extends  RecyclerView.Adapter<GiaoDichAdapter.GiaoDichViewHolder>{
    private List<GiaoDich> giaoDiches;

    public GiaoDichAdapter(List<GiaoDich> giaoDiches) {
        this.giaoDiches = giaoDiches;
    }

    @NonNull
    @Override
    public GiaoDichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gd,parent,false);
        return new GiaoDichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoDichViewHolder holder, int position) {
        GiaoDich giaoDich = giaoDiches.get(position);
        if (giaoDich==null){
            return;
        }
        holder.gd_soTien.setText(String.valueOf(giaoDich.getSoTienGiaoDich()));
        holder.gd_nd.setText(giaoDich.getNoiDungChuyenKhoan());
        String ngaythang = giaoDich.getNgayGiaoDich();
        String thoigian = giaoDich.getGioGiaoDich();
        holder.gd_time.setText(ngaythang + " "+thoigian);
        holder.gd_sodu.setText(String.valueOf(giaoDich.getSoDuLucGui()));
//        holder.gd_loaigd.setText(DbHelper.getLoaiGiaoDich(giaoDich.getLoaiGiaoDichKey()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LoaiGiaoDich/"+giaoDich.getLoaiGiaoDichKey());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    LoaiGiaoDich loaiGiaoDich = snapshot.getValue(LoaiGiaoDich.class);
                    holder.gd_loaigd.setText(loaiGiaoDich.getTenLoai());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(giaoDiches !=null){
            return giaoDiches.size();
        }
        return 0;
    }

    public class GiaoDichViewHolder extends RecyclerView.ViewHolder{
        TextView gd_soTien,gd_nd,gd_time,gd_sodu,gd_loaigd;
        public GiaoDichViewHolder(@NonNull View itemView) {
            super(itemView);
            gd_soTien = itemView.findViewById(R.id.gd_soTien);
            gd_nd = itemView.findViewById(R.id.gd_nd);
            gd_time = itemView.findViewById(R.id.gd_time);
            gd_sodu = itemView.findViewById(R.id.gd_sodu);
            gd_loaigd = itemView.findViewById(R.id.gd_loaigd);
        }
    }
}
