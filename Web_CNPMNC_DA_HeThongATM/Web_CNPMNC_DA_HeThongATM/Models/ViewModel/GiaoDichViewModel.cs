using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class GiaoDichViewModel
    {
        public GiaoDichViewModel(GiaoDich ls, string status, DateTime date, string name1,string name2)
        {
            LSGD = ls;
            this.status = status;
            this.date = date;
            this.name1 = name1;
            this.name2 = name2;
        }
        public GiaoDich LSGD { get; set; }
        public double SoDu { get; set; }
        public string status { get; set; }
        public DateTime date { get; set; }
        public string name1 { get; set; }
        public string name2 { get; set; }

    }
}
