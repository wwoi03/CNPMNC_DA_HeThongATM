using System.ComponentModel.DataAnnotations;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class CardControlViewModel
    {
        public TheNganHang theNganHang { get; set; }
        public string tenKH { get; set; }
        public string CCCD { get; set; }
        public string LoaiThe { get; set; }
        public string TinhTrang { get; set; }
    }
}
