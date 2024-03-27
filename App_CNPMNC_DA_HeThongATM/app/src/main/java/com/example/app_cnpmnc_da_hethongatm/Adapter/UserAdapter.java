package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.UserActivity;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.text.BreakIterator;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserActivity> mListUsers;

    public UserAdapter(List<UserActivity> mListUsers){
        this.mListUsers = mListUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        UserActivity user = mListUsers.get(position);
        if (user ==null){
            return;
        }
        holder.tvId.setText("ID: "+user.getKey());
        holder.tvName.setText("Name: "+user.getTenKhachHang());
        holder.tvsdt.setText("Số Điện Thoại: "+user.getSoDienThoai());
        holder.tvchinhanh.setText("Chi Nhánh: "+user.getChiNhanhKey());
        holder.tvngaydenhen.setText("Ngày hẹn: "+user.getNgayDenHen());

    }

    @Override
    public int getItemCount() {
        if (mListUsers !=null){
            return mListUsers.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvsdt;
        private TextView tvchinhanh;
        private TextView tvngaydenhen;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvsdt = itemView.findViewById(R.id.tv_sdt);
            tvchinhanh = itemView.findViewById(R.id.tv_chinhanh);
            tvngaydenhen = itemView.findViewById(R.id.tv_ngaydenhen);


        }
    }
}
