
using Microsoft.AspNetCore.Mvc.ModelBinding;
using System.ComponentModel.DataAnnotations;
using UniqueIdGenerator;


namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class DatLichHenViewModel
    {
        public string Key{ get; set; }
        [Required(ErrorMessage = "Số điện thoại phải có 10 số.")]
        public string SoDienThoai { get; set; }
        [Required(ErrorMessage = "Vui lòng tên khách hàng.")]
        public string TenKhachHang { get; set; }
        [Required(ErrorMessage = "Vui chọn loại dịch vụ.")]
        public string LoaiDichVu { get; set; }
        public string ChiNhanhKey { get; set; }
        public string MaKHKey { get; set; }
        public string MaNhanVienKey { get; set; }
        [Required(ErrorMessage = "Vui lòng nhập ngày đến hẹn.")]
        public string NgayDenHen { get; set; }
        public int TrangThai { get; set; }
    }
}
