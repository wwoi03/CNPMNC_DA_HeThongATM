namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class KhachHang
    {
        public string CCCD { get; set; }//1
        public string DiaChi { get; set; }//2
        public string Email { get; set; }//3
        public string GioiTinh { get; set; }//4
        public string NgayTao { get; set; } // 5
        public string MaNhanVien {  get; set; }//6
        public string MatKhau { get; set; }//7
        public string SoDienThoai { get; set; }//8
        public string TenKhachHang { get; set; }//9

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
