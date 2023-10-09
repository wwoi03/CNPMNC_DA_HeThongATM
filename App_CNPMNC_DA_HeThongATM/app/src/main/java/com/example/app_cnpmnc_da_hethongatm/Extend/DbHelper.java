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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DbHelper {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static long MY_CARD; // Mã số thẻ ngân hàng của khách hàng đăng nhập vào app

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
<<<<<<< HEAD
                        MY_CARD = (long) dataSnapshot.child("MaSoThe").getValue();

                        Log.d("firebase", String.valueOf(MY_CARD));
=======
                        MY_CARD = Long.parseLong(String.valueOf(dataSnapshot.child("MaSoThe").getValue()));
>>>>>>> s1-Hao-MoKhoaTK
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

    // Lấy danh sách tài khoản liên kết
    public static FirebaseRecyclerOptions<TaiKhoanLienKet> getAffiliateAccounts() {
        FirebaseRecyclerOptions<TaiKhoanLienKet> options = new FirebaseRecyclerOptions.Builder<TaiKhoanLienKet>()
                .setQuery(firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("MaSoThe").equalTo(MY_CARD), TaiKhoanLienKet.class)
                .build();

        return options;
    }

    // tìm tài khoản theo số tài khoản
    /*public static TaiKhoanLienKet getAffiliateAccountByAccountNumber(long soTaiKhoan) {
        firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("SoTaiKhoan").equalTo(soTaiKhoan)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }*/

    // cập nhật số dư
    public static void updateSurplus(String taiKhoanKey, double soDuMoi) {
        Map<String, Object> map = new HashMap<>();
        map.put("SoDu", soDuMoi);

        firebaseDatabase.getReference("TaiKhoanLienKet").child(taiKhoanKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public static void updateSurplus(String taiKhoanKey, double soDuMoi,String NgayGD,double TienDaGD) {
        Map<String, Object> map = new HashMap<>();
        map.put("SoDu", soDuMoi);
        map.put("NgayGD",NgayGD);
        map.put("TienDaGD",TienDaGD);

        firebaseDatabase.getReference("TaiKhoanLienKet").child(taiKhoanKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    // Tạo lịch sử giao dịch
    public static void addTransactionHistory(TaiKhoanLienKet taiKhoanNguon, TaiKhoanLienKet taiKhoanNhan, double soTienGiaoDich, String noiDungChuyenKhoan) {
        LocalTime now = null;
        String timeString="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalTime.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timeString = now.format(formatter);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("GioGiaoDich", timeString);
        map.put("NgayGiaoDich", GetDataForm());
        map.put("SoTaiKhoan", taiKhoanNguon.getSoTaiKhoan());
        map.put("NoiDungChuyenKhoan", noiDungChuyenKhoan);
        map.put("SoTienGiaoDich", soTienGiaoDich);
        map.put("TaiKhoanNhan", taiKhoanNhan.getSoTaiKhoan());
        map.put("SoDuHienTai", taiKhoanNguon.getSoDu());

        String newKey = firebaseDatabase.getReference("ThuHuong").push().getKey(); // tạo key

        firebaseDatabase.getReference("LichSuGiaoDich").child(newKey).setValue(map);
    }

    public static String GetDataForm(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String NgayGDHomNay = sdf.format(calendar.getTime());

        return NgayGDHomNay;
    }
}
