using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

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
        public IActionResult GetNameCus(string cccd)
        {
            string PIN = firebaseHelper.CreatePIN();
            string Stk = firebaseHelper.CreateAccountNumbet();
            KhachHang custommer =  firebaseHelper.GetCustomerbyid(cccd);
            if(custommer == null)
            {
                return Json("null");
            }
            var data = new
            {
                Tenkh = custommer.TenKh,
                Sdt = custommer.Sdt,
                PIN = PIN,
                Stk = Stk
            };
            return Json(data);
        }

        [HttpPost]
        public IActionResult CreateCard(TheNganHangViewModel cardViewModel)
        {
            cardViewModel.MaDangNhap = "Nguyễn Lê Quốc Thuận";
            firebaseHelper.CreateCard(cardViewModel,cardViewModel.CCCD);
            return RedirectToAction("Index");
        }

    }
}
