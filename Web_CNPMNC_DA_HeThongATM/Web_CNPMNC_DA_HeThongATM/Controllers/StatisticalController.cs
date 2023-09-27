using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatisticalController : Controller
    {
        FirebaseHelper firebaseHelper;

        public StatisticalController()
        {
            firebaseHelper = new FirebaseHelper();
        }

        public IActionResult Index()
        {
            ViewBag.customers = firebaseHelper.GetCustomers();
            ViewBag.totalAssets = firebaseHelper.GetTotalAssets();
            ViewBag.totalTransaction = firebaseHelper.GetTotalTransaction();
            return View();
        }
    }
}
