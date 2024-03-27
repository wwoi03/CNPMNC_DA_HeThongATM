using Web_CNPMNC_DA_HeThongATM.Controllers.Factory_method;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.Factorymethod
{
    public class SimpleInterestRateFactory : IInterestRateFactory
    {
        public LaiSuatViewModel CreateInterestRate(string key, string kyHan, double tiLe)
        {
            // Tạo đối tượng LaiSuatViewModel với loại lãi suất đơn giản
            return new LaiSuatViewModel { Key = key, KyHan = kyHan, TiLe = tiLe };
        }
    }
}
