using Microsoft.AspNetCore.Mvc;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CustomerController : Controller
    {
        
        // Danh sách khách hàng
        public IActionResult Index()
        {
            return View();
        }
    }
}
