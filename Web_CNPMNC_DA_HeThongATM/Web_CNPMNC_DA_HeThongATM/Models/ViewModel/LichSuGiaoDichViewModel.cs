using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class LichSuGiaoDichViewModel
    {
        public LichSuGiaoDichViewModel(LichSuGiaoDich ls, string status, DateTime date, string name)
        {
            LSGD = ls;
            this.status = status;
            this.date = date;
            this.name = name;
        }
        public LichSuGiaoDich LSGD { get; set; }
        public string status { get; set; }
        public DateTime date { get; set; }
        public string name { get; set; }
    }
}
