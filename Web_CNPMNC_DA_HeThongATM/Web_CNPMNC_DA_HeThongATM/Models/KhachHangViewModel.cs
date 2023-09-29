using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class KhachHangViewModel
    {
        [Required(ErrorMessage = "Vui lòng nhập mã căn cước.")]
        public string CCCD { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập địa chỉ.")]
        public string Diachi { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập email.")]
        public string Email { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập giới tính.")]
        public string Gioitinh { get; set; }

        public string Makh { get; set; }

        // Kiểm tra không được để trống (null) và có ít nhất một số
        [Required(ErrorMessage = "Vui lòng nhập số điện thoại.")]
        public string Sdt { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập tên khách hàng.")]
        public string Tenkh { get; set; }
        public string NgayTao { get; set; }
    }

}
