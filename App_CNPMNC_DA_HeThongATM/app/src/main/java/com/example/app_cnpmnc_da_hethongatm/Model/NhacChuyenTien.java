package com.example.app_cnpmnc_da_hethongatm.Model;

import android.os.Build;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NhacChuyenTien implements Serializable {
    private String Key;
    private String MaKHKey;
    private String LoaiGiaoDichKey;
    private String NoiDungNhac;
    private String NgayHetHan;
    private long TaiKhoanNhan;
    private double SoTienNhacChuyen;
    private int TrangThai;

    public NhacChuyenTien() {
    }

    public NhacChuyenTien(String key, String maKHKey, String loaiGiaoDichKey, String noiDungNhac, String ngayHetHan, long taiKhoanNhan, double soTienNhacChuyen, int trangThai) {
        Key = key;
        MaKHKey = maKHKey;
        LoaiGiaoDichKey = loaiGiaoDichKey;
        NoiDungNhac = noiDungNhac;
        NgayHetHan = ngayHetHan;
        TaiKhoanNhan = taiKhoanNhan;
        SoTienNhacChuyen = soTienNhacChuyen;
        TrangThai = trangThai;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getMaKHKey() {
        return MaKHKey;
    }

    public void setMaKHKey(String maKHKey) {
        MaKHKey = maKHKey;
    }

    public String getLoaiGiaoDichKey() {
        return LoaiGiaoDichKey;
    }

    public void setLoaiGiaoDichKey(String loaiGiaoDichKey) {
        LoaiGiaoDichKey = loaiGiaoDichKey;
    }

    public String getNoiDungNhac() {
        return NoiDungNhac;
    }

    public void setNoiDungNhac(String noiDungNhac) {
        NoiDungNhac = noiDungNhac;
    }

    public String getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        NgayHetHan = ngayHetHan;
    }

    public long getTaiKhoanNhan() {
        return TaiKhoanNhan;
    }

    public void setTaiKhoanNhan(long taiKhoanNhan) {
        TaiKhoanNhan = taiKhoanNhan;
    }

    public double getSoTienNhacChuyen() {
        return SoTienNhacChuyen;
    }

    public void setSoTienNhacChuyen(double soTienNhacChuyen) {
        SoTienNhacChuyen = soTienNhacChuyen;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getSoTienNhacChuyenFormat() {
        // Sử dụng String.format với định dạng số có dấu phân cách
        return String.format("%,d", (long) SoTienNhacChuyen);
    }

    // kiểm tra quá ngày hạn
    public boolean checkLate() {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.parse(NgayHetHan, formatter);
        }

        LocalDate now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDate.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (date.isBefore(now)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
