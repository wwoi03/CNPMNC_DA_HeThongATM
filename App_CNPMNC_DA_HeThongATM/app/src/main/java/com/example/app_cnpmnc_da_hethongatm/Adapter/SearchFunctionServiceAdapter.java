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

public class SearchFunctionServiceAdapter extends FirebaseRecyclerAdapter<ChucNang, SearchFunctionServiceAdapter.SearchFunctionServiceAdapterVH> {
    Listener listener;

    public SearchFunctionServiceAdapter(@NonNull FirebaseRecyclerOptions<ChucNang> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchFunctionServiceAdapterVH holder, int position, @NonNull ChucNang model) {
        holder.tvName.setText(model.getTenChucNang());

        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public SearchFunctionServiceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchFunctionServiceAdapterVH(view);
    }

    // Xử lý sự kiện
    private void initListener(SearchFunctionServiceAdapterVH holder, int position, ChucNang model) {
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(model);
            }
        });
    }


    class SearchFunctionServiceAdapterVH extends RecyclerView.ViewHolder {
        TextView tvName;

        public SearchFunctionServiceAdapterVH(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public interface Listener {
        void setOnClickItemListener(ChucNang serviceFunction);
    }
}
