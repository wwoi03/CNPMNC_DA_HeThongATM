namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class TheNganHang
    {
        public string LoaiThe { get; set; }
        public string MaDangNhap { get; set; }
        public string MaKH { get; set; } // key trong bảng "KhachHang"
        public int MaPin { get; set; }
        public long MaSoThe { get; set; }
        public string NgayHetHan { get; set; }
        public string NgayMoThe { get; set; }
        public string SDTDangKy { get; set; }
        public int TinhTrangThe { get; set; }
        public double HanMucThe { get; set; }
    }
}
