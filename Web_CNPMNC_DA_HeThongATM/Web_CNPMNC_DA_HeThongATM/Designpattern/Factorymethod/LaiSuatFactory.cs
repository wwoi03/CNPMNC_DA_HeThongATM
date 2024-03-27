using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.Factorymethod
{
    public class LaiSuatFactory : ILaiSuatFactory
    {
        public LaiSuat CreateLaiSuat()
        {
            // Thực hiện logic để tạo một đối tượng LaiSuat
            // Ví dụ:
            return new LaiSuat();
        }
    }

}
