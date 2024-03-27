using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.Factorymethod
{
    public interface ILaiSuatFactory
    {
        LaiSuat CreateLaiSuat();
    }
}
