using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CreateCardController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public async Task<IActionResult> Index()
        {
            ViewData["j"] = await firebaseHelper.GetCustommers();
            return View();
        }
        [HttpPost]
        public IActionResult CreateCard(CustommerViewModel customer)
        {

            return View();
        }
    }
}
