using Microsoft.AspNetCore.Mvc.ModelBinding;
using System.ComponentModel.DataAnnotations;
using UniqueIdGenerator;
namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class TaiKhoanLienKetViewModel
    {
        public string Key { get; set; }
        public string MaKHKey { get; set; }
        public string MaLoaiTKKey { get; set; }


        [Required(ErrorMessage = "Vui lòng nhập số tài khoản")]
        public long? SoTaiKhoan { get; set; }


        public double SoDu { get; set; }
        public string TenTK { get; set; }
        public int TinhTrangTaiKhoan { get; set; }
        public double HanMucTK { get; set; }
        public long TienDaGD { get; set; }
        public long TienGD1Lan { get; set; }
		[RegularExpression(@"^([1-9][0-9]{5,}|100000)$", ErrorMessage = "Số tiền gửi phải trên 100000.")]
		[Required(ErrorMessage = "Vui lòng nhập số tiền")]
		public double? SoTien { get; set; }
    }
}
