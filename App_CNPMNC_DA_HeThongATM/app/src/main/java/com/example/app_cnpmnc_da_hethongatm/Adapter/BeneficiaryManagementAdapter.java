package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.AddBeneficiaryManagementActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.EditBeneficiaryManagementActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.Beneficiary;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BeneficiaryManagementAdapter extends FirebaseRecyclerAdapter<Beneficiary, BeneficiaryManagementAdapter.myViewHolder>{
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
        holder.tv_tkthuhuong.setText(String.valueOf(model.getTKThuHuong()));
        holder.tv_sotaikhoan.setText("Sacombank");
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowthuhuong, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView tv_tengnthuhuong, tv_tkthuhuong, tv_sotaikhoan;
        ImageView img_Popup;

        Beneficiary beneficiary;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tengnthuhuong = itemView.findViewById(R.id.tv_ngthuhuong);
            tv_tkthuhuong = itemView.findViewById(R.id.tv_tkthuhuong);
            tv_sotaikhoan = itemView.findViewById(R.id.tv_sotaikhoan);
            img_Popup = itemView.findViewById(R.id.img_Popup);

            img_Popup.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view);
        }

        public void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_chuyentien:
                    Toast.makeText(itemView.getContext(), "Nội dung thông báo", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_edit:
                    beneficiary = new Beneficiary();
                    Intent intent = new Intent(itemView.getContext(), EditBeneficiaryManagementActivity.class);
                    intent.putExtra("ID", beneficiary.getIdThuHuong());
                    itemView.getContext().startActivity(intent);

                    return true;
                case R.id.action_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa người thụ hưởng này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int position = getAdapterPosition(); // Lấy vị trí
                            String key = getRef(position).getKey();
                            if (key != null) {
                                FirebaseDatabase.getInstance().getReference().child("ThuHuong").child(key).removeValue();
                            } else {
                                Toast.makeText(itemView.getContext(),"Không tìm thấy khóa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(itemView.getContext(),"Đã hủy", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                default:
                    return false;
            }
        }
    }
}
