using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
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
            double sotien = (double)account.SoTien;
            firebaseHelper.RutTien(sotien, (long)account.SoTaiKhoan);
            return RedirectToAction("RutTien");
        }

        public IActionResult ChuyenTien(IFormCollection form)
        {
            // Lấy dữ liệu từ form
            double soTien = Convert.ToDouble(form["SoTien"]);
            long taiKhoanNguoiChuyen = Convert.ToInt64(form["SoTaiKhoanNguoiChuyen"]);
            long taiKhoanNguoiNhan = Convert.ToInt64(form["SoTaiKhoanNguoiNhan"]);

            // Tiếp tục xử lý chuyển tiền
            firebaseHelper.ChuyenTien(soTien, taiKhoanNguoiChuyen, taiKhoanNguoiNhan);

            ViewData["ChuyenTienSuccess"] = true;

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

        // Nạp tiền
        public IActionResult NapTien()
        {
            return View();
        }

        [HttpPost]
        public IActionResult NapTien(TaiKhoanLienKetViewModel account)
        {
            


            ModelState.Remove("Key");
            ModelState.Remove("MaKHKey");
            ModelState.Remove("MaLoaiTKKey");
            ModelState.Remove("TenTK");
			if (ModelState.IsValid)
            {
				    double sotien = (double)account.SoTien;
                   
				    firebaseHelper.NapTien(sotien, (long)account.SoTaiKhoan,(long)account.SoTaiKhoan);
                

				    return RedirectToAction("NapTien");
			}
             return View("NapTien", account);
        }
    }
}
