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
        //view tao the
        public  IActionResult Index()
        {

            return View();
        }
        [HttpGet]
        public IActionResult GetNameCus(string cccd)
        {
            string PIN = firebaseHelper.CreatePin();
            string Stk = firebaseHelper.CreateAccountNumbet();
            KhachHang custommer =  firebaseHelper.GetCustomerbyid(cccd);
            if(custommer == null)
            {
                if (TempData.ContainsKey("SuccessMessage"))
                {
                    ViewBag.SuccessMessage = TempData["SuccessMessage"];
                }

                return Json("null");
            }
            var data = new
            {
                Tenkh = custommer.TenKhachHang,
                Sdt = custommer.SoDienThoai,
                PIN = PIN,
                Stk = Stk
            };
            return Json(data);
        }
        //tao the
        [HttpPost]
        public IActionResult CreateCard(TheNganHangViewModel cardViewModel)
        {
            //lấy khách hàng
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cardViewModel.MaKH);
            if (cardViewModel == null || custommer == null) return RedirectToAction("Index");
            //đẩy lên database
            cardViewModel.MaDangNhap = "Nguyễn Lê Quốc Thuận";
            cardViewModel.TinhTrangThe = 0;
            firebaseHelper.CreateCard(cardViewModel, cardViewModel.MaKH);
            firebaseHelper.CreateCardLink(TaiKhoanLienKet.DefaultCard(cardViewModel.MaPIN, custommer.TenKhachHang, cardViewModel.MaSoThe));
            TempData["SuccessMessage"] = "You successfully created a card.";
            return RedirectToAction("Index");

        }


        //danh sách thẻ atm
        public IActionResult ListCard()
        {
            List<TheNganHang> theNganHangs = firebaseHelper.getListCard();
            
            ViewData["listCard"] = theNganHangs;
            return View();
        }


        //Chỉnh sửa thẻ ngân hàng
        [HttpGet]
        public IActionResult EditCardATM() { 

            return View();
        }

    }
}