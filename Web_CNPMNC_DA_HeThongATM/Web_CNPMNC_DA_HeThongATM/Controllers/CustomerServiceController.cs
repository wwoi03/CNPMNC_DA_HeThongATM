using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CustomerServiceController : Controller
    {
        string TenTK;
        string accountKey;

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        // Rút tiền
        [HttpGet]
        public IActionResult RutTien()
        {
            return View();
        }

        [HttpPost]
        public IActionResult RutTien(TaiKhoanLienKetViewModel account)
        {
            double sotien = account.SoTien;
            firebaseHelper.RutTien(sotien, account.SoTaiKhoan);
            return RedirectToAction("RutTien");
        }

        // Chuyển tiền
        [HttpGet]
        public IActionResult ChuyenTien()
        {
            return View();
        }

        [HttpPost]
        public IActionResult ChuyenTien(TaiKhoanLienKetViewModel account)
        {
            double sotien = account.SoTien;
            firebaseHelper.ChuyenTien(sotien, account.SoTaiKhoan);
            return RedirectToAction("ChuyenTien");
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
    }
}
