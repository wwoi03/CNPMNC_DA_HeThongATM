using Microsoft.AspNetCore.Mvc;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AppointmentController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
        public IActionResult Create()
        {
            return View();
        }
    }
}
