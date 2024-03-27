using Web_CNPMNC_DA_HeThongATM.Designpattern.Singleton;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.Strategy
{
	public class IAppointmentStrategy : ListAppointMentStrategy
	{
		public List<DatLichHen> listAppointment()
		{
			return FirebaseSingleton.GetInstance().GetAppointent();
			
			
		}
	}
}
