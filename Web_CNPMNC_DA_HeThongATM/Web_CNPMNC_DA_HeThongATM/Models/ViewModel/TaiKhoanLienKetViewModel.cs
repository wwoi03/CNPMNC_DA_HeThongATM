using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class TaiKhoanLienKetViewModel
    {
        public string Key { get; set; }
        public string MaKHKey { get; set; }
        public string MaLoaiTKKey { get; set; }
        public long SoTaiKhoan { get; set; }
        [Required(ErrorMessage ="Xin mời nhập số tài khoản")] 
        public double SoDu { get; set; }
        public double SoTien { get; set; }
        [Required(ErrorMessage ="Xin mời nhập số tiền lớn hơn 0")] 
        public string TenTK { get; set; }
        public int TinhTrangTaiKhoan { get; set; }
        public double HanMucTK { get; set; }

       
    }
}
