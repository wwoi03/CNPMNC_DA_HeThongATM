package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.WithdrawSavingsActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListAccountSavingsAdapter extends FirebaseRecyclerAdapter<GuiTietKiem, ListAccountSavingsAdapter.myViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public ListAccountSavingsAdapter(@NonNull FirebaseRecyclerOptions<GuiTietKiem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull GuiTietKiem model) {
        holder.textViewSoTaiKhoan.setText(String.valueOf(model.getTaiKhoanTietKiem()));
        holder.textViewSoTienGui.setText(String.valueOf(model.getTienGui()));
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listaccountsavings, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSoTaiKhoan, textViewSoTienGui;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSoTaiKhoan = itemView.findViewById(R.id.textViewSoTaiKhoan);
            textViewSoTienGui = itemView.findViewById(R.id.textViewSoTienGui);
        }
    }
}