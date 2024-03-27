using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers.Factory_method
{
    public interface IInterestRateFactory
    {
        LaiSuatViewModel CreateInterestRate(string key, string kyHan, double tiLe);
    }
}
