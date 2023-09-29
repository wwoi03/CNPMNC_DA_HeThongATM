using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class Customer : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public async Task<IActionResult> Index()
        {
            ViewData["j"] = await firebaseHelper.GetCustommers();
            return View();
        }
    }
}
