namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class KhachHang
    {
        public string Key { get; set; }
        public string CCCD { get; set; }
        public string TenKH { get; set; }
        public string NgaySinh { get; set; }
        public string GioiTinh { get; set; }
        public string DiaChi { get; set; }
        public string Email { get; set; }
        public string SoDienThoai { get; set; }
        public string NgayTao { get; set; } // "DD/MM/YYYY"
        public string MatKhau { get; set; }
        public string MaNhanVienKey { get; set; }
    }
}