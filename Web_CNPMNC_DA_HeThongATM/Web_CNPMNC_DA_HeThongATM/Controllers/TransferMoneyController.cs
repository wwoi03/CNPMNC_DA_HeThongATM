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
            double sotien = account.SoTien;
            firebaseHelper.ChuyenTien(sotien, account.SoTaiKhoan);
            return RedirectToAction("Index");
        }
       
    }
}
