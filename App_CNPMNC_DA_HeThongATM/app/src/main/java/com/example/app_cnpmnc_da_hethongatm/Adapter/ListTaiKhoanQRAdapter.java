package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.WithdrawSavingsActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListTaiKhoanQRAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, ListTaiKhoanQRAdapter.myViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ListTaiKhoanQRAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull TaiKhoanLienKet model) {
        holder.tv_tkqr.setText(String.valueOf(model.getSoTaiKhoan()));
        holder.tv_tentkqr.setText(String.valueOf(model.getTenTK()));
        holder.tv_soduqr.setText(String.valueOf(model.getSoDu()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WithdrawSavingsActivity.class);
                intent.putExtra("Key", model.getKey());
                view.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listqr, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tkqr, tv_tentkqr, tv_soduqr;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tkqr = itemView.findViewById(R.id.tv_tkqr);
            tv_tentkqr = itemView.findViewById(R.id.tv_tentkqr);
            tv_soduqr = itemView.findViewById(R.id.tv_soduqr);
        }
    }
}