package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;


public class BeneficiaryManagementAdapter extends RecyclerView.Adapter<BeneficiaryManagementAdapter.BeneficiaryManagementVH> implements Filterable {
    Context context;
    ArrayList<Beneficiary> beneficiaryArrayList;


    public BeneficiaryManagementAdapter(Context context, ArrayList<Beneficiary> beneficiaryArrayList) {
        this.context = context;
        this.beneficiaryArrayList = beneficiaryArrayList;
    }

    @NonNull
    @Override
    public BeneficiaryManagementVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowthuhuong, parent,false);
        return new BeneficiaryManagementVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryManagementVH holder, int position) {
        Beneficiary beneficiary = beneficiaryArrayList.get(position);
        holder.tv_cardnumber.setText(beneficiary.getSoThe());
        holder.tv_ngthuhuong.setText(beneficiary.getName());
        holder.tv_bankname.setText(beneficiary.getBankName());
    }




    @Override
    public int getItemCount() {
        return beneficiaryArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class BeneficiaryManagementVH extends RecyclerView.ViewHolder{
        TextView tv_ngthuhuong, tv_cardnumber, tv_bankname;
        public BeneficiaryManagementVH(@NonNull View itemView) {
            super(itemView);
            tv_ngthuhuong = itemView.findViewById(R.id.tv_ngthuhuong);
            tv_cardnumber = itemView.findViewById(R.id.tv_cardnumber);
            tv_bankname = itemView.findViewById(R.id.tv_bankname);
        }
    }

}
