using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class RoleViewModel
    {
        [Display(Name = "Tên chức vụ")]
        [Required(ErrorMessage = "Phải nhập {0}")]
        [StringLength(50, MinimumLength = 3, ErrorMessage = "Độ dài {0} phải từ {2} đến {1} ký tự")]
        public string name { get; set; }
        public string Key { get; set; }
    }
}