using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CreditCardController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public  IActionResult Index()
        {   
            return View();
        }
       
        //check cccd
        [HttpGet]
        public async Task<IActionResult> ActionName(string cccd)
        {
          
           var idcccd =  await  firebaseHelper.CheckCCCDExist(cccd);
           Console.WriteLine(idcccd);
            return Json(idcccd);
        }
        [HttpGet]
        public async Task<IActionResult> GetNameCus(string codeToFind)
        {
            
            KhachHangViewModel custommer = await firebaseHelper.GetCustomerbyid(codeToFind);
           
            return Json(custommer);
        }

    }
}
