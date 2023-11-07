using System.ComponentModel.DataAnnotations;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class ChucNangViewModel
    {
        public string Key { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập mã dịch vụ.")]
        [StringLength(50, ErrorMessage = "Chiều dài vượt quá 50 ký tự")]
        [Display(Name = "Mã dịch vụ")]
        public string MaChucNang { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập tên dịch vụ.")]
        [StringLength(50, ErrorMessage = "Chiều dài vượt quá 50 ký tự")]
        [Display(Name = "Tên dịch vụ")]
        public string TenChucNang { get; set; }

        [Required(ErrorMessage = "Vui lòng thêm icon dịch vụ.")]
        [Display(Name = "Icon dịch vụ")]
        public IFormFile ImageFile { get; set; }

        [Required(ErrorMessage = "Vui lòng thêm icon dịch vụ.")]
        public string IconChucNang { get; set; }

        public ChucNang ConvertClassModel()
        {
            ChucNang chucNang = new ChucNang()
            {
                MaChucNang = MaChucNang,
                TenChucNang = TenChucNang,
                IconChucNang = IconChucNang
            };

            return chucNang;
        }

        public void ConvertViewModel(ChucNang chucNang)
        {
            Key = chucNang.Key;
            MaChucNang = chucNang.MaChucNang;
            TenChucNang = chucNang.TenChucNang;
            IconChucNang = chucNang.IconChucNang;
        }
    }
}
