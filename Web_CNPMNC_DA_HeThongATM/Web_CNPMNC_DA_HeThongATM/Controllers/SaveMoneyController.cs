using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Drawing;
using System.Globalization;
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
            GiaoDich giaoDich;
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


                JObject data = JObject.Parse(guiTietKiem.TaiKhoanLienKet);
                 giaoDich = new GiaoDich
                {
                    Key = "",
                    GioGiaoDich = DateTime.Now.ToString("HH:mm:ss"),
                    NgayGiaoDich = Day(),
                    LoaiGiaoDichKey = "1",
                    NoiDungChuyenKhoan = "Nạp tiền tài khoản tiết kiệm",
                    PhiGiaoDich = (double)guiTietKiem.TienGui * 0.001,
                    SoDuLucGui = (double)data["soDu"],
                    SoTienGiaoDich = (double)guiTietKiem.TienGui,
                    SoDuLucNhan = (double)data["soDu"]- (double)guiTietKiem.TienGui,                  
                    TaiKhoanNguon = (long)data["soTaiKhoan"],
                    TaiKhoanNhan = guiTietKiem.TaiKhoanTietKiem,
                };

                firebaseHelper.GuiTietKiem(tietKiemk, taiKhoan);


               
                firebaseHelper.LichSuGD("1", giaoDich);
                TempData["Message"] = "Gửi tiết kiệm";
                return RedirectToAction("OpenSavingsHome");
            }
            ViewBag.OpenSavingsHome = firebaseHelper.GetNameLaiSuat();
            TempData["Fail"] = "Gửi tiết kiệm";
            return View("OpenSavingsHome", guiTietKiem);
        }
        //tất toán tiết kiệm
        [HttpGet]
        public IActionResult SettlementOfSavings()
        {
            
            return View();
        }
        [HttpPost]
        public IActionResult SettlementOfSavings(GuiTietKiemViewModel guiTietKiemView)
        {
            
            ModelState.Remove("Key");
            ModelState.Remove("NgayGui");
            ModelState.Remove("TienLaiKyHan");
            ModelState.Remove("TaiKhoanLienKet");
            ModelState.Remove("LaiSuatKey");
            ModelState.Remove("TienLaiKyHan");
            ModelState.Remove("TienGui");
            if (ModelState.IsValid)
            {
                JObject data = JObject.Parse(guiTietKiemView.TaiKhoanLienKet);
                //lấy kì hạn
                string kihan = (string)data["laiSuat"]["kyHan"];
                //lấy ngày gửi theo số tài khoản tiết kiểm
                string ngaygui = (string)data["guiTietKiem"]["ngayGui"];
                //bước lấy số tiền tiết kiệm tiền lãi + tới kỳ
                double tienrut = (double)data["guiTietKiem"]["tienGui"] + (double)data["guiTietKiem"]["tienLaiToiKy"];
                //kiểm tra ngày gửi đúng lấy số tiền lãi sai thì thông báo không thể tất toán đợi đến ngày .... 
                //nếu rút thành công thì lưu lịch sử và xóa sổ tiết kiệm
                DateTime futureDate;
                if (CompareDate(ngaygui, kihan,out futureDate))
                {

                }
                else
                {
                    TempData["Fail"]="Chỉ rút được sau ngày " + futureDate.ToString();
                    View("SettlementOfSavings", guiTietKiemView);
                }
                

            }
            return View("SettlementOfSavings",guiTietKiemView);
        }
        //nạp tiết kiệm
        [HttpGet]
        public IActionResult GetTKNorTKTK(long stk)
        {
            GuiTietKiem  guiTietKiem =  firebaseHelper.GetAccontSaveMoney(stk);
            LaiSuat laiSuat = firebaseHelper.GetLaiSuat(guiTietKiem.LaiSuatKey);
            TaiKhoanLienKet taiKhoanLien = firebaseHelper.GetTaiKhoanLienKetSTK(guiTietKiem.TaiKhoanNguon);
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
            GiaoDich giaoDich;
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


                     giaoDich = new GiaoDich
                    {
                        Key = "",
                        GioGiaoDich = DateTime.Now.ToString("HH:mm:ss"),
                        NgayGiaoDich = Day(),
                        LoaiGiaoDichKey = "1",
                        NoiDungChuyenKhoan = "Nạp tiền tài khoản tiết kiệm",
                        PhiGiaoDich = (double)guiTietKiemView.TienGui*0.001,
                        SoDuLucGui = (double)data["guiTietKiem"]["tienGui"],
                        SoDuLucNhan = (double)data["guiTietKiem"]["tienGui"] + (double)guiTietKiemView.TienGui,
                        SoTienGiaoDich = (double)guiTietKiemView.TienGui,
                        TaiKhoanNguon = (long)data["guiTietKiem"]["taiKhoanNguon"],
                        TaiKhoanNhan = (long)data["guiTietKiem"]["taiKhoanTietKiem"],
                    };
                    firebaseHelper.LichSuGD("4", giaoDich);
                    TempData["Message"] = "Gửi tiết kiệm";
                    return RedirectToAction("AdmitMoney");



                }
            }

            TempData["Fail"] = "Gửi tiết kiệm";
            return View("AdmitMoney",guiTietKiemView);
        }

        //check ngày để rút
        public bool CompareDate(string dateString, string kihan, out DateTime futureDate)
        {
            DateTimeFormatInfo dtfi = new DateTimeFormatInfo();
            dtfi.ShortDatePattern = "MM/dd/yyyy";
            dtfi.DateSeparator = "/";
            DateTime objDate = Convert.ToDateTime(dateString, dtfi);

            string[] parts = kihan.Split(' ');
            string numberPart = parts[0];
            // Thêm tháng vào ngày
            futureDate = objDate.AddMonths(int.Parse(numberPart));

            // So sánh với ngày hiện tại
            if (futureDate > DateTime.Now)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }
}
