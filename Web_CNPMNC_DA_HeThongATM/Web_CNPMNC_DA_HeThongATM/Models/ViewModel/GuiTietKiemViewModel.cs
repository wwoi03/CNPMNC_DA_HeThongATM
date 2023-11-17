using System.ComponentModel.DataAnnotations;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class GuiTietKiemViewModel
    {

        public string Key { get; set; }

           
        public long TaiKhoanTietKiem { get; set; }
        [Required(ErrorMessage = "Vui lòng nhập số tài khoản nguồn.")]
        public long? TaiKhoanNguon { get; set; }

        [Required(ErrorMessage = "Vui lòng chọn số lãi.")]
        [Range(1, int.MaxValue, ErrorMessage = "Vui lòng chọn số lãi hợp lệ.")]
        public string LaiSuatKey { get; set; }

        
        public string NgayGui { get; set; }

       
        public double TienLaiKyHan { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập số tiền gửi.")]
        [RegularExpression(@"^([1-9][0-9]{5,}|100000)$", ErrorMessage = "Số tiền gửi phải trên 100000.")]
        public double? TienGui { get; set; }

       
        public string TaiKhoanLienKet { get; set; }
    }
}
