using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class TheNganHangViewModel
    {
        [Required(ErrorMessage = "Mã KH không được để trống.")]
        // mã khách hàng tại đây là căn cước công dân bên bảng khách hàng
        public string MaKH { get; set; }

        [Required(ErrorMessage = "Mã PIN không được để trống.")]
        public string MaPin { get; set; }

        [Required(ErrorMessage = "Mã số thẻ không được để trống.")]
        public long MaSoThe { get; set; }

        [Required(ErrorMessage = "Ngày mở thẻ không được để trống.")]
        public string NgayMoThe { get; set; }

        [Required(ErrorMessage = "Ngày hết hạn không được để trống.")]
        public string NgayHetHan { get; set; }

        [Required(ErrorMessage = "Số điện thoại đăng ký không được để trống.")]
        public string SDTDangKy { get; set; }

        [Required(ErrorMessage = "Tình trạng thẻ không được để trống.")]
        public int TinhTrangThe { get; set; }

        [Required(ErrorMessage = "Loại thẻ không được để trống.")]
        public string LoaiThe { get; set; }
        public string MaDangNhap { get; set; }

        public string TenKhachHang { get; set; }

        public string TenLoaiThe { get; set; }
    }
}