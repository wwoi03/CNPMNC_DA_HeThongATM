using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Drawing;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class SaveMoneyController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        private string Day()
        {
            // Lấy ngày tháng năm hiện tại
            DateTime currentDate = DateTime.Now;

            // Lấy ngày
            int day = currentDate.Day;

            // Lấy tháng
            int month = currentDate.Month;

            // Lấy năm
            int year = currentDate.Year;
            return day + "/" + month + "/" + year;
        }
        public IActionResult OpenSavingsHome()
        {
            ViewBag.OpenSavingsHome = firebaseHelper.GetNameLaiSuat();
            return View();

        }
        [HttpGet]
        public IActionResult getInfomationCus(long sotaikhoannguon)
        {
            TaiKhoanLienKet taiKhoanLien = firebaseHelper.GetTaiKhoanLienKetSTK(sotaikhoannguon);
            return Json(taiKhoanLien);
        }
        [HttpPost]
        public IActionResult SaveMoney(GuiTietKiemViewModel guiTietKiem)
        {
            //xử lý mật khẩu nhân viên tại đây



            //sử lý trừ tiền
            TaiKhoanLienKet taiKhoan = JsonConvert.DeserializeObject<TaiKhoanLienKet>(guiTietKiem.TaiKhoanLienKet);
            GuiTietKiem tietKiemk = new GuiTietKiem
            {
                Key = "",
                TaiKhoanTietKiem = 0,
                MaKHKey = taiKhoan.MaKHKey,
                TaiKhoanNguon = taiKhoan.SoTaiKhoan,
                LaiSuatKey = "",
                NgayGui = Day(),
                TienLaiToiKy = 0,
                TienGui = guiTietKiem.TienGui
            };
            firebaseHelper.GuiTietKiem(tietKiemk, taiKhoan);
            return RedirectToAction("OpenSavingsHome");
        }
    }
}
