package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListBeneficiaryAdapter extends FirebaseRecyclerAdapter<ThuHuong, ListBeneficiaryAdapter.myViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public ListBeneficiaryAdapter(@NonNull FirebaseRecyclerOptions<ThuHuong> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ThuHuong model) {

        holder.tv_usernamenote.setText(model.getTenNguoiThuHuong());
        holder.tv_idnote.setText(String.valueOf(model.getTKThuHuong()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TransferMoneyActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("tkthuhuong", model);
                view.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thuhuong, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_usernamenote, tv_idnote;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idnote = itemView.findViewById(R.id.tv_idnote);
            tv_usernamenote = itemView.findViewById(R.id.tv_usernamnote);
        }
    }
}