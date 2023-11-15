using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class LaiSuatViewModel
    {
        [Required(ErrorMessage = "Mã lãi suất là trường bắt buộc.")]
        public string Key { get; set; }

        [Required(ErrorMessage = "Kỳ hạn là trường bắt buộc.")]
        public string KyHan { get; set; }

        [Required(ErrorMessage = "Tỉ lệ là trường bắt buộc.")]
        public double TiLe { get; set; }
    }
}
