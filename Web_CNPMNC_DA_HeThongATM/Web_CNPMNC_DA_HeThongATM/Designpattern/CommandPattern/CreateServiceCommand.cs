using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.CommandPattern
{
    public class CreateServiceCommand : ICommand
    {
        private readonly FirebaseHelper _firebaseHelper;
        private readonly ChucNang _chucNang;

        public CreateServiceCommand(FirebaseHelper firebaseHelper, ChucNang chucNang)
        {
            _firebaseHelper = firebaseHelper;
            _chucNang = chucNang;
        }

        public void Execute()
        {
            _firebaseHelper.CreateService(_chucNang);
        }
    }
}
