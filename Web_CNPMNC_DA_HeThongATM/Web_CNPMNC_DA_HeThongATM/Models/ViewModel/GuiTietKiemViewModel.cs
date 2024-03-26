using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class GuiTietKiemViewModel
    {

        public string Key { get; set; }
        public long TaiKhoanTietKiem { get; set; }
        public long TaiKhoanNguon { get; set; }
        public string LaiSuatKey { get; set; }
        public string NgayGui { get; set; }
        public double TienLaiKyHan { get; set; }
        public double TienGui { get; set; }

        public string TaiKhoanLienKet {  get; set; }
    }
}
