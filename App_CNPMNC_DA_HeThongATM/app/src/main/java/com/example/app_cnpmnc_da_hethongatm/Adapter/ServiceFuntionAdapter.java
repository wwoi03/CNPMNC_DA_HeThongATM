package com.example.app_cnpmnc_da_hethongatm.Adapter;

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

        customItemDecoration(holder, position);
        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public ServiceFuntionAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_transaction_service_function, parent, false);
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

    // custom item
    private void customItemDecoration(ServiceFuntionAdapterVH holder, int position) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();

        if (position % 3 == 0 || position % 3 == 2) {
            layoutParams.bottomMargin = holder.itemView.getResources().getDimensionPixelOffset(R.dimen.dp_1);
        } else {
            layoutParams.leftMargin = holder.itemView.getResources().getDimensionPixelOffset(R.dimen.dp_1);
            layoutParams.rightMargin = holder.itemView.getResources().getDimensionPixelOffset(R.dimen.dp_1);
            layoutParams.bottomMargin = holder.itemView.getResources().getDimensionPixelOffset(R.dimen.dp_1);
        }
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
