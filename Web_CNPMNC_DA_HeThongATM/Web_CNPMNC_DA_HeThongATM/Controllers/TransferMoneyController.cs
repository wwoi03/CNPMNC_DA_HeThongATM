using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    
    public class TransferMoneyController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        string TenTK;
        string accountKey;
        string TenTK1;
        public IActionResult Index()
        {

            return View();
        }
        [HttpGet]
        public IActionResult sendAccount(long SoTaiKhoan)
        {
            string tinhtrang = "undefined";
            if (firebaseHelper.GetAccountbyid(SoTaiKhoan) != null)
            {
                TenTK = firebaseHelper.GetAccountbyid(SoTaiKhoan).TenTK;
                return Json(TenTK);              
            }
            else
            {
                return Json("Không tìm thấy");
            }
        }

        [HttpPost]
        public IActionResult ChuyenTien(TaiKhoanLienKetViewModel account)
        {
            double soTien = account.SoTien;
            long taiKhoanNguoiChuyen = account.SoTaiKhoanNguoiChuyen;
            long taiKhoanNguoiNhan = account.SoTaiKhoanNguoiNhan;

            // Assuming firebaseHelper is an instance of your FirebaseHelper class
            firebaseHelper.ChuyenTien(soTien, taiKhoanNguoiChuyen, taiKhoanNguoiNhan);

            return RedirectToAction("Index");
        }


    }
}
