
using Microsoft.AspNetCore.Mvc.ModelBinding;
using System.ComponentModel.DataAnnotations;
using System.Globalization;
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
   //     public DatLichHenViewModel(string inputDate)
   //     {
   //         if (DateTime.TryParseExact(inputDate, "yyy/MM/dd", CultureInfo.InvariantCulture, DateTimeStyles.None, out DateTime parsedDate))
   //         {
   //             NgayDenHen = parsedDate;
   //         }
   //         else
   //         {
			//	throw new ArgumentException("Ngày không hợp lệ", nameof(inputDate));
			//}
   //     }
        public int TrangThai { get; set; }
    }
}
