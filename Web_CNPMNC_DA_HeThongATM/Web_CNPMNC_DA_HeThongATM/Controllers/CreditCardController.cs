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
            //lấy khách hàng
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cardViewModel.MaKH);
            if (cardViewModel == null || custommer == null) return RedirectToAction("Index");
            //đẩy lên database
            cardViewModel.MaDangNhap = "Nguyễn Lê Quốc Thuận";
            firebaseHelper.CreateCard(cardViewModel, cardViewModel.MaKH);
            firebaseHelper.CreateCardLink(TaiKhoanLienKet.DefaultCard(cardViewModel.MaPIN, custommer.TenKh, cardViewModel.MaSoThe));

            return RedirectToAction("Index");

        }

    }
}
