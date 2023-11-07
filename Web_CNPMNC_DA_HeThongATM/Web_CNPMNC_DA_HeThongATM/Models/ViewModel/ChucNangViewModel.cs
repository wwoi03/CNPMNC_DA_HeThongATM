using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class ChucNangViewModel
    {
        public string Key { get; set; }
        public string MaChucNang { get; set; }
        public string TenChucNang { get; set; }
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
    }
}
