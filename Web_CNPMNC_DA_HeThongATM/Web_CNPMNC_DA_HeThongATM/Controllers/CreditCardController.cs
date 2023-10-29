using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    /*
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
            cardViewModel.MaDangNhap = "1234";
            cardViewModel.TinhTrangThe = 0;
            firebaseHelper.CreateCard(cardViewModel, firebaseHelper.GetKeysBycccd(cardViewModel.MaKH));
            firebaseHelper.CreateCardLink(TaiKhoanLienKet.DefaultCard(cardViewModel.MaPin, custommer.TenKhachHang, cardViewModel.MaSoThe));
            TempData["SuccessMessage"] = "You successfully created a card.";
            return RedirectToAction("Index");

        }


        //danh sách thẻ atm
        public IActionResult ListCard()
        {
            List<TheNganHang> theNganHangs = firebaseHelper.getListCard();
            List<ListTheNganHangViewModel> listThes = new List<ListTheNganHangViewModel>();
            foreach (var item in theNganHangs)
            {
                var list = new ListTheNganHangViewModel
                {
                    MaKH = item.MaKH,
                    TenKhachHang = firebaseHelper.GetNameCustomerbyid(item.MaKH),
                    MaPin = item.MaPin,
                    MaDangNhap = item.MaDangNhap,
                    MaSoThe = item.MaSoThe,
                    TinhTrangThe = item.TinhTrangThe,
                    NgayMoThe = item.NgayMoThe,
                    NgayHetHan = item.NgayHetHan,
                    SDTDangKy = item.SDTDangKy,
                    LoaiThe = item.LoaiThe,
                    TenLoaiThe = firebaseHelper.GetNameTypeCard(item.LoaiThe),
                    

                };
                listThes.Add(list);
             }
            ViewData["listCard"] = listThes;
            return View(listThes);
        }
        /*
        //Chỉnh sửa thẻ ngân hàng {load form}
        [HttpGet]
        public IActionResult EditCardATM(long MaSoThe) { 

            TheNganHang theNganHang = firebaseHelper.GetTheNganHangById(MaSoThe);
            ViewBag.TenKhachHang = firebaseHelper.GetNameCustomerbyid(theNganHang.MaKH);
            ViewBag.Details = theNganHang;
            return View();
        }


        //Chỉnh sửa the ngân hàng {nhận dữ liệu}
        [HttpPost]
        public IActionResult EditCardATM(TheNganHangViewModel theNganHang)
        {
            return View();
        }


        //tìm kiếm theo cccd theo sdt theo masothe
        public IActionResult SearchCard(string searchValue)
        {
           
            TheNganHang theNganHangs = firebaseHelper.SearchCard(searchValue);
            ListTheNganHangViewModel ViewThes = new ListTheNganHangViewModel();
            ViewThes.MaKH=theNganHangs.MaKH;
            ViewThes.TenKhachHang = firebaseHelper.GetNameCustomerbyid(theNganHangs.MaKH);
            ViewThes.SDTDangKy = theNganHangs.SDTDangKy;
            ViewThes.MaDangNhap = theNganHangs.MaDangNhap;
            ViewThes.MaSoThe = theNganHangs.MaSoThe;
            ViewThes.NgayHetHan = theNganHangs.NgayHetHan;
            ViewThes.NgayMoThe = theNganHangs.NgayMoThe;
            ViewThes.MaPin=theNganHangs.MaPin;
            ViewThes.TinhTrangThe = theNganHangs.TinhTrangThe;
            ViewThes.LoaiThe = theNganHangs.LoaiThe;
            ViewThes.TenLoaiThe = firebaseHelper.GetNameTypeCard(theNganHangs.LoaiThe);
            ViewBag.IteamSeach = ViewThes;
            return PartialView("SearchCard", ViewThes);

        }
        */
}
