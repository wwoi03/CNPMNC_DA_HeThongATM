package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class ServiceFuntionAdapter extends FirebaseRecyclerAdapter<ChucNang, ServiceFuntionAdapter.ServiceFuntionAdapterVH> {
    Listener listener;

    public ServiceFuntionAdapter(@NonNull FirebaseRecyclerOptions<ChucNang> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ServiceFuntionAdapterVH holder, int position, @NonNull ChucNang model) {
        holder.tvServiceFunctionName.setText(model.getTenChucNang());
        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public ServiceFuntionAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transaction_service_function_item, parent, false);
        return new ServiceFuntionAdapterVH(view);
    }

    // Xử lý sự kiện
    private void initListener(ServiceFuntionAdapterVH holder, int position, ChucNang model) {
        holder.tvServiceFunctionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(model, getRef(position));
            }
        });
    }

    class ServiceFuntionAdapterVH extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvServiceFunctionName;

        public ServiceFuntionAdapterVH(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvServiceFunctionName = itemView.findViewById(R.id.tvServiceFunctionName);
        }
    }

    public interface Listener {
        void setOnClickItemListener(ChucNang serviceFunction, DatabaseReference databaseReference);
    }
}
