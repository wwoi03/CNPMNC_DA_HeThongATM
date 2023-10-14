using Microsoft.AspNetCore.Mvc;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatementController : Controller
    {
        public IActionResult CustomerStatement()
        {
            return View();
        }
    }
}
