using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
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
            
            ModelState.Remove("Key");
            ModelState.Remove("NgayGui");
            ModelState.Remove("TienLaiKyHan");
            ModelState.Remove("TaiKhoanLienKet");       
            if (ModelState.IsValid)
            {
                TaiKhoanLienKet taiKhoan = JsonConvert.DeserializeObject<TaiKhoanLienKet>(guiTietKiem.TaiKhoanLienKet);
                GuiTietKiem tietKiemk = new GuiTietKiem
                {
                    Key = "",
                    TaiKhoanTietKiem = 0,
                    MaKHKey = taiKhoan.MaKHKey,
                    TaiKhoanNguon = taiKhoan.SoTaiKhoan,
                    LaiSuatKey = guiTietKiem.LaiSuatKey,
                    NgayGui = Day(),
                    TienLaiToiKy = 0,
                    TienGui = (double) guiTietKiem.TienGui
                };
                firebaseHelper.GuiTietKiem(tietKiemk, taiKhoan);
                TempData["Message"] = "Gửi tiết kiệm";
                return RedirectToAction("OpenSavingsHome");
            }
            ViewBag.OpenSavingsHome = firebaseHelper.GetNameLaiSuat();
            TempData["Fail"] = "Gửi tiết kiệm";
            return View("OpenSavingsHome", guiTietKiem);
        }


        //nạp tiết kiệm
        [HttpGet]
        public IActionResult GetTKNorTKTK(long stk)
        {
            GuiTietKiem  guiTietKiem =  firebaseHelper.GetAccontSaveMoney(stk);
            LaiSuat laiSuat = firebaseHelper.GetLaiSuat(guiTietKiem.LaiSuatKey);
            TaiKhoanLienKet taiKhoanLien = firebaseHelper.GetTaiKhoanLienKetSTK(guiTietKiem.TaiKhoanTietKiem);
            var data = new
            {
                guiTietKiem,
                laiSuat,
                taiKhoanLien.TenTK,

            };
            return Json(data);
        }
        [HttpGet]
        public IActionResult AdmitMoney()
        {
            
            return View();
        }

        [HttpPost]
        public IActionResult AdmitMoney(GuiTietKiemViewModel guiTietKiemView)
        {
            ModelState.Remove("Key");
            ModelState.Remove("NgayGui");
            ModelState.Remove("TienLaiKyHan");
            ModelState.Remove("TaiKhoanLienKet");
            ModelState.Remove("LaiSuatKey");
            ModelState.Remove("TienLaiKyHan");
            ModelState.Remove("TaiKhoanNguon");
            if (guiTietKiemView.TaiKhoanLienKet != null && ModelState.IsValid)
            {
                JObject data = JObject.Parse(guiTietKiemView.TaiKhoanLienKet);

              

                if (firebaseHelper.AdmitSaveMoney((double)guiTietKiemView.TienGui, (string)data["guiTietKiem"]["key"]))
                {


                    GiaoDich giaoDich = new GiaoDich
                    {
                        Key = "",
                        GioGiaoDich = DateTime.Now.ToString("HH:mm:ss"),
                        NgayGiaoDich = Day(),
                        LoaiGiaoDichKey = "1",
                        NoiDungChuyenKhoan = "Nạp tiền tài khoản tiết kiệm",
                        PhiGiaoDich = 0,
                        SoDuLucGui = (double)data["guiTietKiem"]["tienGui"],
                        SoDuLucNhan = (double)data["guiTietKiem"]["tienGui"] + (double)guiTietKiemView.TienGui,
                        SoTienGiaoDich = (double)guiTietKiemView.TienGui,
                        TaiKhoanNguon = (long)data["guiTietKiem"]["taiKhoanNguon"],
                        TaiKhoanNhan = (long)data["guiTietKiem"]["taiKhoanTietKiem"],
                    };
                    firebaseHelper.LichSuGD("1", giaoDich);
                    TempData["Message"] = "Gửi tiết kiệm";
                    return RedirectToAction("AdmitMoney");



                }
            }

            TempData["Fail"] = "Gửi tiết kiệm";
            return View("AdmitMoney",guiTietKiemView);
        }
    }
}
