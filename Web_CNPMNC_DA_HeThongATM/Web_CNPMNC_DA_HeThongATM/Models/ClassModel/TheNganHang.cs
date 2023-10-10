namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class TheNganHang
    {
        public string LoaiThe { get; set; }
        public string MaDangNhap { get; set; }
        public string MaKH { get; set; } // key trong bảng "KhachHang"
        public string MaPin { get; set; }
        public long MaSoThe { get; set; }
        public string NgayHetHan { get; set; }
        public string NgayMoThe { get; set; }
        public string SDTDangKy { get; set; }
        public int TinhTrangThe { get; set; }
    }
}
//============================================================================================NOTE==================================================================================================================
//•	LoaiThe(string): lưu trữ mã loại thẻ (key của record bên Loại thẻ)
//•	MaDangNhan(string): đang phân tích chưa cần quan tầm
//•	MaKH (string): lưu trữ mã khách hàng (key của record bên khách hàng)
//•	MaPin(string): mã Pin ATM (6 số)
//•	MaSoThe(long): lưu mã số thẻ ngân hàng (16 số) (dây liên kết tới các bảng có MaSoThe)
//•	NgayHetHan(string): định dạng ngày/tháng/năm
//•	NgayMoThe (string): định dạng ngày/tháng/năm
//•	SDTDangKy (string): đang phân tích
//•	TinhTrangThe (int): 
//o   0 – bình thường
//o	1 – khóa thẻ

