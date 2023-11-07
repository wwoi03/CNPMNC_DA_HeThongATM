using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ServiceController : Controller
    {
        FirebaseHelper firebaseHelper;

        public ServiceController()
        {
            firebaseHelper = new FirebaseHelper();
        }

        public IActionResult Index()
        {
            @ViewBag.pageTitle = "Dịch Vụ";
            ViewBag.listService = firebaseHelper.GetListService();

            return View();
        }
    }
}
