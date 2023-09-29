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
        [HttpGet]
        public IActionResult GetNameCus(string codeToFind)
        {
            string PIN = firebaseHelper.CreatePIN();
            string Stk = firebaseHelper.CreateAccountNumbet();
            KhachHangViewModel custommer =  firebaseHelper.GetCustomerbyid(codeToFind);
            if(custommer == null)
            {
                return NotFound();
            }
            var data = new
            {
                Tenkh = custommer.Tenkh,
                Sdt = custommer.Sdt,
                PIN = PIN,
                Stk = Stk
            };
            return Json(data);
        }

        [HttpPost] 
        public IActionResult CreateCard(CardViewModel cardViewModel)
        {
            cardViewModel.MaDangNhap = "Nguyễn Lê Quốc Thuận";
           firebaseHelper.CreateCard(cardViewModel);
            return RedirectToAction("Index");
        }

    }
}
