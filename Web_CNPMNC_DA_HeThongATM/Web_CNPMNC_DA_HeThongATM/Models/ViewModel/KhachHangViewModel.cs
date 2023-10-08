using Microsoft.AspNetCore.Mvc.ModelBinding;
using System.ComponentModel.DataAnnotations;
using UniqueIdGenerator;


namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class KhachHangViewModel
    {
        [Required(ErrorMessage = "Vui lòng nhập mã căn cước.")]
        public string CCCD { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập địa chỉ.")]
        [RegularExpression(@"^[^,]+, [^,]+, [^,]+, [^,]+$", ErrorMessage = "Địa chỉ phải chứa đủ thông tin Số nhà, Phường/Xã, Quận/Huyện, Tỉnh/Thành phố.")]
        public string DiaChi { get; set; }


        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập email.")]
        public string Email { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập giới tính.")]
        public string GioiTinh { get; set; }

        // Kiểm tra không được để trống (null) và có ít nhất một số
        [Required(ErrorMessage = "Vui lòng nhập số điện thoại.")]
        [RegularExpression(@"^\d{10}$", ErrorMessage = "Số điện thoại phải có ít nhất 10 chữ số.")]
        public string Sdt { get; set; }

        // Kiểm tra không được để trống (null)
        [Required(ErrorMessage = "Vui lòng nhập tên khách hàng.")]
        public string TenKh { get; set; }

        public string NgayTao { get; set; }

        public string MatKhau { get; set; }
    }

}