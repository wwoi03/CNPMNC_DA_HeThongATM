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

        public IActionResult ChuyenTien(IFormCollection form)
        {
            // Lấy dữ liệu từ form
            double soTien = Convert.ToDouble(form["SoTien"]);
            long taiKhoanNguoiChuyen = Convert.ToInt64(form["SoTaiKhoanNguoiChuyen"]);
            long taiKhoanNguoiNhan = Convert.ToInt64(form["SoTaiKhoanNguoiNhan"]);

            // Tiếp tục xử lý chuyển tiền
            firebaseHelper.ChuyenTien(soTien, taiKhoanNguoiChuyen, taiKhoanNguoiNhan);

            ViewData["ChuyenTienSuccess"] = true;

            return RedirectToAction("Index");
        }

        }
}
