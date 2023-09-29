using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class CardViewModel
    {
        [Required(ErrorMessage = "Mã KH không được để trống.")]
        public string MaKH { get; set; }

        [Required(ErrorMessage = "Mã PIN không được để trống.")]
        public string MaPIN { get; set; }

        [Required(ErrorMessage = "Mã số thẻ không được để trống.")]
        public string MaSoThe { get; set; }

        [Required(ErrorMessage = "Ngày mở thẻ không được để trống.")]
        public string NgayMoThe { get; set; }

        [Required(ErrorMessage = "Ngày hết hạn không được để trống.")]
        public string NgayHetHan { get; set; }

        [Required(ErrorMessage = "Số điện thoại đăng ký không được để trống.")]
        public string SDTDangKy { get; set; }

        [Required(ErrorMessage = "Tình trạng thẻ không được để trống.")]
        public string TinhTrangThe { get; set; }

        [Required(ErrorMessage = "Loại thẻ không được để trống.")]
        public string Loaithe { get; set; }
        public string MaDangNhap { get; set; }
    }
}
