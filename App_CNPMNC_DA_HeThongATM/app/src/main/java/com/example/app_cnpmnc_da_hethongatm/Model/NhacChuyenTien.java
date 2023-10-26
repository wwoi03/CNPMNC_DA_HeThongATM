package com.example.app_cnpmnc_da_hethongatm.Model;

public class NhacChuyenTien {
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
}
