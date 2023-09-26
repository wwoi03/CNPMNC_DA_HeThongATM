package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BeneficiaryManagementAdapter extends FirebaseRecyclerAdapter<Beneficiary, BeneficiaryManagementAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BeneficiaryManagementAdapter(@NonNull FirebaseRecyclerOptions<Beneficiary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Beneficiary model) {
        holder.tv_tengnthuhuong.setText(model.getTenNguoiThuHuong());
        holder.tv_tkthuhuong.setText(model.getTkThuHuong());
        holder.tv_sotaikhoan.setText("Sacombank");


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowthuhuong, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tengnthuhuong, tv_tkthuhuong, tv_sotaikhoan;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tengnthuhuong = itemView.findViewById(R.id.tv_ngthuhuong);
            tv_tkthuhuong = itemView.findViewById(R.id.tv_tkthuhuong);
            tv_sotaikhoan = itemView.findViewById(R.id.tv_sotaikhoan);
        }
    }
}
