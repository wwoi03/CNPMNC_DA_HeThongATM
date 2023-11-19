package com.example.app_cnpmnc_da_hethongatm.Activities;

import java.util.HashMap;
import java.util.Map;

public class UserActivity {
    private String Key;
    private String TenKhachHang;
    private String SoDienThoai;
    private String LoaiDichVu;
    private String ChiNhanhKey;
    private String MaKHKey;
    private String MaNhanVienKey;
    private String NgayDenHen;
    private int TrangThai;

    public UserActivity(String name, String soDienThoai, String chiNhanhKey, String ngayDenHen) {
    }

    public UserActivity(String key, String tenKhachHang, String soDienThoai, String loaiDichVu, String chiNhanhKey, String maKHKey, String maNhanVienKey, String ngayDenHen, int trangThai) {
        Key = key;
        TenKhachHang = tenKhachHang;
        SoDienThoai = soDienThoai;
        LoaiDichVu = loaiDichVu;
        ChiNhanhKey = chiNhanhKey;
        MaKHKey = maKHKey;
        MaNhanVienKey = maNhanVienKey;
        NgayDenHen = ngayDenHen;
        TrangThai = trangThai;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getLoaiDichVu() {
        return LoaiDichVu;
    }

    public void setLoaiDichVu(String loaiDichVu) {
        LoaiDichVu = loaiDichVu;
    }

    public String getChiNhanhKey() {
        return ChiNhanhKey;
    }

    public void setChiNhanhKey(String chiNhanhKey) {
        ChiNhanhKey = chiNhanhKey;
    }

    public String getMaKHKey() {
        return MaKHKey;
    }

    public void setMaKHKey(String maKHKey) {
        MaKHKey = maKHKey;
    }

    public String getMaNhanVienKey() {
        return MaNhanVienKey;
    }

    public void setMaNhanVienKey(String maNhanVienKey) {
        MaNhanVienKey = maNhanVienKey;
    }

    public String getNgayDenHen() {
        return NgayDenHen;
    }

    public void setNgayDenHen(String ngayDenHen) {
        NgayDenHen = ngayDenHen;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("TenKhachHang",TenKhachHang);
    result.put("SoDienThoai",SoDienThoai);
    result.put("LoaiDichVu",LoaiDichVu);
    result.put("ChiNhanhKey",ChiNhanhKey);
    result.put("MaKHKey",MaKHKey);
    result.put("MaNhanVienKey",MaNhanVienKey);
    result.put("NgayDenHen",NgayDenHen);
    result.put("TrangThai",TrangThai);

    return result;
}
}

