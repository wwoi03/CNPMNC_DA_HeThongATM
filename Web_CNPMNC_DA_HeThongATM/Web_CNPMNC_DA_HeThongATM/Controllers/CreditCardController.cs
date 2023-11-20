using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using PagedList;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CreditCardController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        //view tao the
        public IActionResult Index()
        {

            return View();
        }
        [HttpGet]
        public IActionResult GetNameCus(string cccd)
        {
            string PIN = firebaseHelper.CreatePin();
            string masothe = firebaseHelper.CreateAccountNumbet();
            string sotaikhoan = firebaseHelper.CreateSotaikhoan();
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cccd);
            if (custommer == null)
            {
                if (TempData.ContainsKey("SuccessMessage"))
                {
                    ViewBag.SuccessMessage = TempData["SuccessMessage"];
                }

                return Json("null");
            }
            var data = new
            {
                Tenkh = custommer.TenKH,
                PIN = PIN,
                Stk = masothe,
                sotaikhoan = sotaikhoan
            };
            return Json(data);
        }
        //tao the
        [HttpPost]
        public IActionResult CreateCard(TheNganHangViewModel cardViewModel)
        {
            //lấy khách hàng
            ModelState.Remove("Key");          
            ModelState.Remove("MaKhachHang");
            ModelState.Remove("MaKhachHangKey");
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cardViewModel.CCCD);

            if (cardViewModel == null || custommer == null && !ModelState.IsValid) return View("Index",cardViewModel);
            //đẩy lên database                 
            firebaseHelper.CreateCard(cardViewModel.TheNganHang());
            firebaseHelper.CreateCardLink(TaiKhoanLienKet.DefaultCard(cardViewModel, custommer));
            TempData["SuccessMessage"] = "You successfully created a card.";
            return RedirectToAction("Index");
        }


        //danh sách thẻ atm
        public async Task<IActionResult> ListCard(int? page)
        {

            List<TheNganHang> theNganHangs = await firebaseHelper.getListCard();


            int pageSize = 10;
            int pageeNumber = (page ?? 1);
            List<ListTheNganHangViewModel> listThes = (await Task.WhenAll(theNganHangs.Select(async item => new ListTheNganHangViewModel
            {
                MaKH = item.MaKHKey,
                TenKhachHang = await firebaseHelper.GetNameCustomerbyid2(item.MaKHKey),
                MaPin = item.MaPin,
                MaSoThe = item.MaSoThe,
                TinhTrangThe = item.TinhTrangThe,
                NgayMoThe = item.NgayMoThe,
                NgayHetHan = item.NgayHetHan,
                SoTaiKhoan = item.SoTaiKhoan,
            }))).ToList();
            ViewData["listCard"] = listThes.ToPagedList(pageeNumber, pageSize);
            return View(listThes);
        }

        //Chỉnh sửa thẻ ngân hàng {load form}
        [HttpGet]
        public IActionResult EditCardATM(long MaSoThe)
        {

            TheNganHang theNganHang = firebaseHelper.GetTheNganHangById(MaSoThe);
            ViewBag.TenKhachHang = firebaseHelper.GetNameCustomerbyid(theNganHang.MaKHKey);
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
        public async Task<IActionResult> SearchCard(string searchValue)
        {

            TheNganHang theNganHangs = firebaseHelper.SearchCard(searchValue);
            ListTheNganHangViewModel ViewThes = new ListTheNganHangViewModel();
            ViewThes.MaKH = theNganHangs.MaKHKey;
            ViewThes.TenKhachHang = await firebaseHelper.GetNameCustomerbyid2(theNganHangs.MaKHKey);
            ViewThes.MaSoThe = theNganHangs.MaSoThe;
            ViewThes.NgayHetHan = theNganHangs.NgayHetHan;
            ViewThes.NgayMoThe = theNganHangs.NgayMoThe;
            ViewThes.MaPin = theNganHangs.MaPin;
            ViewThes.TinhTrangThe = theNganHangs.TinhTrangThe;
            ViewBag.IteamSeach = ViewThes;
            return PartialView("SearchCard", ViewThes);

        }
    }
}