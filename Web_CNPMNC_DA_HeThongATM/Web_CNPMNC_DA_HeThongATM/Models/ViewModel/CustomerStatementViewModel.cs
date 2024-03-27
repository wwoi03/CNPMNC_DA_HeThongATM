using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class CustomerStatementViewModel
    {
        [Required(ErrorMessage = "Vui lòng ngày vào ô này.")]
        public string fromDate { get; set; }
        [Required(ErrorMessage = "Vui lòng ngày vào ô này.")]
        public string toDate { get; set; }
        [Required(ErrorMessage = "Vui lòng nhập mã căn cước.")]
        public string cccd { get; set; }

    }
}