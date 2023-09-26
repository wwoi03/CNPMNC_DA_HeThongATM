using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ReditCardController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public async Task<IActionResult> Index()
        {
            ViewData["j"] = await firebaseHelper.GetCustommers();
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> CreateCard(CustommerViewModel customer)
        {
            customer.Makh = await firebaseHelper.CreateidCus();
            //await firebaseHelper.InsertCustommer(customer);
           await firebaseHelper.InsertCustommer(customer);
            return RedirectToAction("Index");
        }
        //check cccd
        [HttpGet]
        public async Task<IActionResult> ActionName(string cccd)
        {
          
           var a =  await  firebaseHelper.CheckCCCDExist(cccd);
           Console.WriteLine(a);
            return Json(a);
        }
        [HttpGet]
        public async Task<IActionResult> a()
        {
            return View();
        }

    }
}
