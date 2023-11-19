using System.ComponentModel.DataAnnotations;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class TheNganHangViewModel
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();


        public string Key { get; set; }
        public string MaKHKey { get; set; }
        public long MaSoThe { get; set; }
        public long SoTaiKhoan { get; set; }
        public string MaPin { get; set; }
        public string NgayHetHan { get; set; }
        public string NgayMoThe { get; set; }
        public int TinhTrangThe { get; set; }

        public string CCCD { get; set; }
        public string MaKH { get; internal set; }
        public string TenKhachHang { get; set; }

        public TheNganHang TheNganHang()
        {
            return new TheNganHang
            {
                Key = this.Key,
                MaKHKey = firebaseHelper.GetKeysBycccd(this.CCCD),
                MaSoThe = this.MaSoThe,
                SoTaiKhoan = this.SoTaiKhoan,
                MaPin = this.MaPin,
                NgayHetHan = this.NgayHetHan,
                NgayMoThe = this.NgayMoThe,
                TinhTrangThe = this.TinhTrangThe,
            };
        }
    }
}


//[Required(ErrorMessage = "Mã KH không được để trống.")]
//      // mã khách hàng tại đây là căn cước công dân bên bảng khách hàng
//      public string MaKH { get; set; }

//      [Required(ErrorMessage = "Mã PIN không được để trống.")]
//      public string MaPin { get; set; }

//      [Required(ErrorMessage = "Mã số thẻ không được để trống.")]
//      public long MaSoThe { get; set; }

//      [Required(ErrorMessage = "Ngày mở thẻ không được để trống.")]
//      public string NgayMoThe { get; set; }

//      [Required(ErrorMessage = "Ngày hết hạn không được để trống.")]
//      public string NgayHetHan { get; set; }

//      [Required(ErrorMessage = "Số điện thoại đăng ký không được để trống.")]
//      public string SDTDangKy { get; set; }

//      [Required(ErrorMessage = "Tình trạng thẻ không được để trống.")]
//      public int TinhTrangThe { get; set; }

//      [Required(ErrorMessage = "Loại thẻ không được để trống.")]
//      public string LoaiThe { get; set; }
//      public string MaDangNhap { get; set; }

//      public string TenKhachHang { get; set; }

//      public string TenLoaiThe { get; set; }