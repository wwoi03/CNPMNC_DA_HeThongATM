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

import java.util.ArrayList;

public class SearchFunctionServiceAdapter extends RecyclerView.Adapter<SearchFunctionServiceAdapter.SearchFunctionServiceAdapterVH> {
    Listener listener;
    ArrayList<ChucNang> chucNangs;

    public SearchFunctionServiceAdapter(Listener listener, ArrayList<ChucNang> chucNangs) {
        this.listener = listener;
        this.chucNangs = chucNangs;
    }

    @NonNull
    @Override
    public SearchFunctionServiceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchFunctionServiceAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFunctionServiceAdapterVH holder, int position) {
        ChucNang chucNang = chucNangs.get(position);

        holder.tvName.setText(chucNang.getTenChucNang());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(chucNang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chucNangs.size();
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
