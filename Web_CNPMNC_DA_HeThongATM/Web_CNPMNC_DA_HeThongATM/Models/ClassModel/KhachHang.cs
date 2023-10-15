namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class KhachHang
    {
        public string CCCD { get; set; }
        public string DiaChi { get; set; }
        public string Email { get; set; }
        public string GioiTinh { get; set; }
        public string NgayTao { get; set; } // "DD/MM/YYYY"
        public string SoDienThoai { get; set; }
        public string TenKhachHang { get; set; }

        public string MatKhau { get; set; }
    }
}
//============================================================================================NOTE==================================================================================================================
//•	CCCD(string):  căn cước công dân
//•	DiaChi (string): …
//•	Email(string): …
//•	GioiTinh(string): nam, nữ, không xác định
//•	MaNhanVien (string): lưu mã nhân viên (key của record) tạo tài khoản cho khách hàng
//•	MatKhau (string):
//•	NgayTao(string): định dạng theo ngày/tháng/năm
//•	SoDienThoai (string):
//•	TenKhachHang(string): full hoa và không dấu
