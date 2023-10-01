package com.example.app_cnpmnc_da_hethongatm.Extend;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_cnpmnc_da_hethongatm.Adapter.BeneficiaryAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DbHelper {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static long MY_CARD;

    public interface FirebaseListener {
        void onSuccessListener();
        void onFailureListener(Exception e);
    }

    // Lấy số thẻ ngân hàng
    public static void getCardNumber(String customerKey) {
        Query card = firebaseDatabase.getReference("TheNganHang").orderByChild("MaKH").equalTo(customerKey);

        card.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MY_CARD = (long) dataSnapshot.child("MaSoThe").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Lấy danh sách chức năng
    public static FirebaseRecyclerOptions<ChucNang> getServiceFunctions() {
        FirebaseRecyclerOptions<ChucNang> options = new FirebaseRecyclerOptions.Builder<ChucNang>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ChucNang"), ChucNang.class)
                .build();

        return options;
    }

    // Lấy danh sách người thụ hưởng
    public static FirebaseRecyclerOptions<ThuHuong> getBeneficiaries(long maSoThe) {
        FirebaseRecyclerOptions<ThuHuong> options = new FirebaseRecyclerOptions.Builder<ThuHuong>()
                .setQuery(firebaseDatabase.getReference("ThuHuong").orderByChild("MaSoThe").equalTo(maSoThe), ThuHuong.class)
                .build();

        return options;
    }

    // Sửa người thụ hưởng
    public static void updateBeneficiary(ThuHuong thuHuong, String thuHuongKey, FirebaseListener firebaseListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("TKThuHuong", (long) thuHuong.getTKThuHuong());
        map.put("TenNguoiThuHuong", thuHuong.getTenNguoiThuHuong());

        firebaseDatabase.getReference("ThuHuong").child(thuHuongKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null)
                            firebaseListener.onSuccessListener();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (firebaseListener != null)
                            firebaseListener.onFailureListener(e);
                    }
                });
    }

    // Sửa người thụ hưởng
    public static void addBeneficiary(ThuHuong thuHuong, FirebaseListener firebaseListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("TKThuHuong", (long) thuHuong.getTKThuHuong());
        map.put("MaSoThe", (long) thuHuong.getMaSoThe());
        map.put("TenNguoiThuHuong", thuHuong.getTenNguoiThuHuong());

        String newKey = firebaseDatabase.getReference("ThuHuong").push().getKey(); // tạo key

        firebaseDatabase.getReference("ThuHuong").child(newKey).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null)
                            firebaseListener.onSuccessListener();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (firebaseListener != null)
                            firebaseListener.onFailureListener(e);
                    }
                });
    }

    // Xóa người thụ hưởng
    public static void deleteBeneficiary(String thuHuongKey, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("ThuHuong").child(thuHuongKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null)
                            firebaseListener.onSuccessListener();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (firebaseListener != null)
                            firebaseListener.onFailureListener(e);
                    }
                });
    }
}
