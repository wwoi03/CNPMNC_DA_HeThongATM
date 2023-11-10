using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ManageCardAndAccountController : Controller
    {
        string TenTK;
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        public IActionResult MoKhoaTaiKhoan()
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
        public IActionResult TrangThaiTk(long SoTaiKhoan, int tinhTrangTaiKhoan)
        {
            if (tinhTrangTaiKhoan == 0 || tinhTrangTaiKhoan == 1)
            {
                if (firebaseHelper.TrangThaiTk(SoTaiKhoan, tinhTrangTaiKhoan))
                {
                    // Successfully updated the status, get the updated status message
                    string updatedStatusMessage = firebaseHelper.GetAccountStatusMessage(tinhTrangTaiKhoan);

                    // You can pass the updated status message to the view or use it as needed
                    ViewBag.StatusMessage = updatedStatusMessage;

                    return RedirectToAction("MoKhoaTaiKhoan");
                }
                else
                {
                    // Xử lý khi thay đổi trạng thái không thành công
                    return View("Error");
                }
            }
            else
            {
                // Xử lý khi giá trị tinhTrangTaiKhoan không hợp lệ
                return View("Error");
            }
        }
    }
}
