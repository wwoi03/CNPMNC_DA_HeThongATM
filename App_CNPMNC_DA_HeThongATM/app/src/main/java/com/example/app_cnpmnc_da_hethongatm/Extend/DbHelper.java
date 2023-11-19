package com.example.app_cnpmnc_da_hethongatm.Extend;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.app_cnpmnc_da_hethongatm.Adapter.BeneficiaryAdapter;
import com.example.app_cnpmnc_da_hethongatm.Model.ChucNang;
import com.example.app_cnpmnc_da_hethongatm.Model.GiaoDich;
import com.example.app_cnpmnc_da_hethongatm.Model.HanMucChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.KhachHang;
import com.example.app_cnpmnc_da_hethongatm.Model.LoaiGiaoDich;
import com.example.app_cnpmnc_da_hethongatm.Model.MauChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.NhacChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.ThuHuong;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
import java.util.Random;
import java.util.Set;

public class DbHelper {
    static ProgressBar progressBar;
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public interface FirebaseListener {
        void onSuccessListener();
        void onFailureListener(Exception e);
        void onSuccessListener(DataSnapshot snapshot);
    }

    // bật ProgressDialog
    public static void showProgressDialog(ProgressBar _progressBar) {
        progressBar = _progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }

    // tắt ProgressDialog
    public static void dismissProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    // Lấy số thẻ ngân hàng
    /*public static void getCardNumber(String customerKey) {
        Query card = firebaseDatabase.getReference("TheNganHang").orderByChild("MaKH").equalTo(customerKey);

        card.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MY_CARD = (long) dataSnapshot.child("MaSoThe").getValue();

                        Log.d("firebase", String.valueOf(MY_CARD));
                        MY_CARD = Long.parseLong(String.valueOf(dataSnapshot.child("MaSoThe").getValue()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    // Lấy danh sách chức năng
    public static FirebaseRecyclerOptions<ChucNang> getServiceFunctions() {
        FirebaseRecyclerOptions<ChucNang> options = new FirebaseRecyclerOptions.Builder<ChucNang>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ChucNang"), ChucNang.class)
                .build();

        return options;
    }

    // Lấy danh sách người thụ hưởng
    public static FirebaseRecyclerOptions<ThuHuong> getBeneficiaries(String maKHKey) {
        FirebaseRecyclerOptions<ThuHuong> options = new FirebaseRecyclerOptions.Builder<ThuHuong>()
                .setQuery(firebaseDatabase.getReference("ThuHuong").orderByChild("MaKHKey").equalTo(maKHKey), ThuHuong.class)
                .build();

        return options;
    }

    // Sửa người thụ hưởng
    public static void updateBeneficiary(ThuHuong thuHuong, String thuHuongKey, Context context, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("SoTaiKhoan").equalTo(thuHuong.getTKThuHuong())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
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
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Tài khoản không tồn tại");
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    // Thêm người thụ hưởng
    public static void addBeneficiary(ThuHuong thuHuong, Context context, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("SoTaiKhoan").equalTo(thuHuong.getTKThuHuong())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Kiểm tra xem TenNguoiThuHuong đã tồn tại chưa
                            firebaseDatabase.getReference("ThuHuong").orderByChild("TenNguoiThuHuong").equalTo(thuHuong.getTenNguoiThuHuong())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (!dataSnapshot.exists()) {
                                                // Tạo bản ghi mới nếu TenNguoiThuHuong không tồn tại
                                                String newKey = firebaseDatabase.getReference("ThuHuong").push().getKey();

                                                Map<String, Object> map = new HashMap<>();
                                                map.put("Key", newKey);
                                                map.put("TKThuHuong", (long) thuHuong.getTKThuHuong());
                                                map.put("MaKHKey", thuHuong.getMaKHKey());
                                                map.put("TenNguoiThuHuong", thuHuong.getTenNguoiThuHuong());

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
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                builder.setTitle("Tên đã tồn tại");
                                                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            System.out.println("Lỗi: " + databaseError.getMessage());
                                        }
                                    });
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Tài khoản không tồn tại");
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Lỗi: " + databaseError.getMessage());
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
    public static FirebaseRecyclerOptions<TaiKhoanLienKet> getAffiliateAccounts(String maKHKey) {
        FirebaseRecyclerOptions<TaiKhoanLienKet> options = new FirebaseRecyclerOptions.Builder<TaiKhoanLienKet>()
                .setQuery(firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("MaKHKey").equalTo(maKHKey), TaiKhoanLienKet.class)
                .build();

        return options;
    }

    // lấy danh sách hạn mức chuyển tiền
    public static FirebaseRecyclerOptions<HanMucChuyenTien> getTransferMoneyLimit() {
        FirebaseRecyclerOptions<HanMucChuyenTien> options = new FirebaseRecyclerOptions.Builder<HanMucChuyenTien>()
                .setQuery(firebaseDatabase.getReference("HanMucChuyenTien").orderByChild("HanMuc"), HanMucChuyenTien.class)
                .build();

        return options;
    }

    // cập nhật hạn mức mới
    public static void updateTransferMoneyLimit(String taiKhoanKey, double hanMucMoi, FirebaseListener firebaseListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("HanMucTK", hanMucMoi);

        firebaseDatabase.getReference("TaiKhoanLienKet").child(taiKhoanKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null) {
                            firebaseListener.onSuccessListener();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    //Lấy danh sách LoaiGiaoDic
//    public static String getLoaiGiaoDich(String keygiaodich){
//        String abc = "";
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("LoaiGiaoDich/"+keygiaodich);
//        Log.d("LoaiGiaoDich/"+keygiaodich, "getLoaiGiaoDich: ");
//        myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    LoaiGiaoDich loaiGiaoDich = dataSnapshot.getValue(LoaiGiaoDich.class);
//                    Log.d(loaiGiaoDich.getTenLoai(), "onDataChange: ");
//                    if (loaiGiaoDich != null) {
//                        abc = loaiGiaoDich.getTenLoai();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return abc;
//    }
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
    public static void SaveBill(MauChuyenTien mauChuyenTien){
        Map<String, Object> map = new HashMap<>();
        map.put("MaKHKey",mauChuyenTien.getMaKHKey());
        map.put("NoiDung",mauChuyenTien.getNoiDung());
        map.put("SoTien",mauChuyenTien.getSoTien());
        map.put("TaiKhoanNhan",mauChuyenTien.getTaiKhoanNhan());
        map.put("TenNguoiNhan",mauChuyenTien.getTenNguoiNhan());
        String newKey = firebaseDatabase.getReference("MauChuyenTien").push().getKey();
        map.put("Key",newKey);
        firebaseDatabase.getReference("MauChuyenTien").child(newKey).setValue(map);
    }
    public static void BuilderXinXo(Context context,String TenLoi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage(TenLoi);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Tạo lịch sử giao dịch - Hưng
    public static String addTransactionHistory(TaiKhoanLienKet taiKhoanNguon, TaiKhoanLienKet taiKhoanNhan, double soTienGiaoDich, String noiDungChuyenKhoan,String loaigd) {
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
        map.put("TaiKhoanNguon", taiKhoanNguon.getSoTaiKhoan());
        map.put("NoiDungChuyenKhoan", noiDungChuyenKhoan);
        map.put("SoTienGiaoDich", soTienGiaoDich);
        map.put("TaiKhoanNhan", taiKhoanNhan.getSoTaiKhoan());
        map.put("SoDuLucGui", taiKhoanNguon.getSoDu()-soTienGiaoDich);
        map.put("LoaiGiaoDichKey",loaigd);
        String newKey = firebaseDatabase.getReference("GiaoDich").push().getKey(); // tạo key
        map.put("Key",newKey);
        map.put("PhiGiaoDich",0);
        map.put("SoDuLucNhan", taiKhoanNhan.getSoDu()+soTienGiaoDich);
        firebaseDatabase.getReference("GiaoDich").child(newKey).setValue(map);
        return newKey;
    }

    public static String GetDataForm(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String NgayGDHomNay = sdf.format(calendar.getTime());

        return NgayGDHomNay;
    }

    // Lấy loại tài khoản theo mã loại
    public static void getAccountTypeByKey(String accountTypeKey, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("LoaiTaiKhoan").child(accountTypeKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (firebaseListener != null) {
                                firebaseListener.onSuccessListener(snapshot);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // lấy loại giao dịch theo mã loại giao dịch
    public static void getTransactionTypeByTransactionTypeCode(String maLoaiGD, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("LoaiGiaoDich").orderByChild("MaLoaiGD").equalTo(maLoaiGD)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (firebaseListener != null) {
                                    firebaseListener.onSuccessListener(dataSnapshot);
                                }

                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // lấy danh sách nhắc chuyển tiền
    public static FirebaseRecyclerOptions<NhacChuyenTien> getReminderTransferMoneys(String maKHKey) {
        FirebaseRecyclerOptions<NhacChuyenTien> options = new FirebaseRecyclerOptions.Builder<NhacChuyenTien>()
                .setQuery(firebaseDatabase.getReference("NhacChuyenTien")
                        .orderByChild("MaKHKey")
                        .equalTo(maKHKey), NhacChuyenTien.class)
                .build();

        return options;
    }

    // cập nhật nhắc chuyển tiền
    public static void updateReminderTransferMoney(NhacChuyenTien nhacChuyenTien, FirebaseListener firebaseListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("NoiDungNhac", nhacChuyenTien.getNoiDungNhac());
        map.put("NgayHetHan", nhacChuyenTien.getNgayHetHan());
        map.put("TaiKhoanNhan", nhacChuyenTien.getTaiKhoanNhan());
        map.put("SoTienNhacChuyen", nhacChuyenTien.getSoTienNhacChuyen());
        map.put("TrangThai", nhacChuyenTien.getTrangThai());

        firebaseDatabase.getReference("NhacChuyenTien").child(nhacChuyenTien.getKey()).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null) {
                            firebaseListener.onSuccessListener();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    // xóa nhắc chuyển tiền
    public static void deleteReminderTransferMoney(String nhacChuyenTienKey, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("NhacChuyenTien").child(nhacChuyenTienKey).removeValue()
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

    // thêm nhắc chuyển tiền
    public static void addReminderTransferMoney(String maKHKey, TaiKhoanLienKet taiKhoanNhan, String content, String dateLimit, double money, FirebaseListener firebaseListener) {
        String newKey = firebaseDatabase.getReference("GiaoDich").push().getKey(); // tạo key

        Map<String, Object> map = new HashMap<>();
        map.put("Key", newKey);
        map.put("MaKHKey", maKHKey);
        map.put("LoaiGiaoDichKey", "");
        map.put("NoiDungNhac", content);
        map.put("NgayHetHan", dateLimit);
        map.put("TaiKhoanNhan", taiKhoanNhan.getSoTaiKhoan());
        map.put("SoTienNhacChuyen", money);
        map.put("TrangThai", 0);

        firebaseDatabase.getReference("NhacChuyenTien").child(newKey).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (firebaseListener != null) {
                            firebaseListener.onSuccessListener();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (firebaseListener != null) {
                            firebaseListener.onFailureListener(e);
                        }
                    }
                });
    }

    // tìm kiếm tài khoản theo số tài khoản
    public static void getAccountByAccountNumber(long soTaiKhoan, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("TaiKhoanLienKet").orderByChild("SoTaiKhoan").equalTo(soTaiKhoan)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (firebaseListener != null) {
                            firebaseListener.onSuccessListener(snapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;

        long randomAccountNumber = 1000000000L + ((long)random.nextInt(900000000) * 10) + random.nextInt(10);
        accountNumber = String.valueOf(randomAccountNumber);

        return accountNumber;
    }

    /*******************************************/
    private static String soTaiKhoan;
    public static  String getAcountIDbyCusKey(String cuskey) {
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        databaseReference.child("TaiKhoanLienKet")
                .orderByChild("MaKHKey").equalTo(cuskey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            soTaiKhoan = String.valueOf( snap.child("SoTaiKhoan").getValue());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        return soTaiKhoan;
    }

    // Lấy thẻ ngân hàng theo tài khoản
    public static void GetCardByAccountNumber(long soTaiKhoan, FirebaseListener firebaseListener) {
        firebaseDatabase.getReference("TheNganHang").orderByChild("SoTaiKhoan").equalTo(soTaiKhoan)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (firebaseListener != null) {
                                firebaseListener.onSuccessListener(snapshot);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public static void RegisterNewUser(KhachHang khachHang){
        String newKey = firebaseDatabase.getReference("KhachHang").push().getKey(); // tạo key
        Map<String, Object> map = new HashMap<>();
        map.put("Key", newKey);
        map.put("DiaChi",khachHang.getDiaChi());
        map.put("CCCD",khachHang.getCCCD());
        map.put("Email",khachHang.getEmail());
        map.put("GioiTinh",khachHang.getGioiTinh());
        map.put("MaNhanVienKey",0);
        map.put("MatKhau",khachHang.getMatKhau());
        map.put("NgaySinh",khachHang.getNgaySinh());
        map.put("NgayTao",DbHelper.GetDataForm());
        map.put("SoDienThoai",khachHang.getSoDienThoai());
        map.put("TenKH",khachHang.getTenKH());
        firebaseDatabase.getReference("KhachHang").child(newKey).setValue(map);
    }
    public static void NewTaiKhoanLienKet(KhachHang khachHang){
        String newKey = firebaseDatabase.getReference("TaiKhoanLienKet").push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("HanMucTk", 50000000);
        map.put("Key",newKey);
        map.put("MaKHKey",newKey);
        map.put("MaLoaiTKKey",0);
        map.put("NgayGD",DbHelper.GetDataForm());
        map.put("SoDu",0);
        map.put("SoTaiKhoan",khachHang.getSoDienThoai());
        map.put("SoTien",0);
        map.put("TenTK",khachHang.getTenKH());
        map.put("TienDaGD",0);
        map.put("TienGD1Lan",0);
        map.put("TinhTrangTaiKhoan",0);
        firebaseDatabase.getReference("TaiKhoanLienKet").child(newKey).setValue(map);
    }
}
