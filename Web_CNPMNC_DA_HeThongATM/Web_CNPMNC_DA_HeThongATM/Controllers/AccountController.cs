using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AccountController : Controller
    {

        public static IFirebaseClient client;
        FirebaseHelper firebaseHelper;

        public AccountController()
        {
            firebaseHelper = new FirebaseHelper();
        }



      //--------------------------------------------------ACCOUNT------------------------------------------------------------

        public IActionResult Index()
        {
            Dictionary<String, LoaiTaiKhoan> danhsachloaitaikhoan = firebaseHelper.GetAccWithKey();
            ViewBag.danhsachloaitaikhoan = danhsachloaitaikhoan;
            return View(danhsachloaitaikhoan);
        }

        //TẠO LOẠI TÀI KHOẢN
        public IActionResult CreateAccount()
        {
            return View();
        }

        [HttpPost]
        public IActionResult CreateAcc(LoaiTaiKhoanViewModel taiKhoanViewModel)
        {
            LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan()
            {
                TenLoaiTaiKhoan = taiKhoanViewModel.TenLoaiTaiKhoan,

            };

            firebaseHelper.CreateAccount(loaiTaiKhoan);
            return RedirectToAction("Index", "Account");
        }

        // HIỂN THỊ THÔNG TIN LOẠI TÀI KHOẢN CẦN SỬA
        [HttpGet]
        public IActionResult EditAcc(String editKey)
        {
            Dictionary<string, LoaiTaiKhoan> danhSachLoaiTaiKhoan = firebaseHelper.GetAccWithKey();
            ViewBag.danhSachLoaiTaiKhoan = danhSachLoaiTaiKhoan;

            if (ViewBag.danhSachLoaiTaiKhoan.TryGetValue(editKey, out LoaiTaiKhoan danhsach))
            {
                return View(danhsach);
            }
            return View(danhSachLoaiTaiKhoan);
        }

        //HIỂN THỊ THÔNG TIN NHƯNG KHÔNG ĐƯỢC SỬA
        [HttpGet]
        public IActionResult DetailAcc(String editKey)
        {
            Dictionary<string, LoaiTaiKhoan> danhSachLoaiTaiKhoan = firebaseHelper.GetAccWithKey();
            ViewBag.danhSachLoaiTaiKhoan = danhSachLoaiTaiKhoan;

            if (ViewBag.danhSachLoaiTaiKhoan.TryGetValue(editKey, out LoaiTaiKhoan danhsach))
            {
                return View(danhsach);
            }
            return View(danhSachLoaiTaiKhoan);
        }

        //SỬA LOẠI TÀI KHOẢN
        [HttpPost]
        public IActionResult EditAcc(LoaiTaiKhoan editedAcc)
        {
            // Trích xuất thông tin từ biểu mẫu và cập nhật vào cơ sở dữ liệu
            if (ModelState.IsValid)
            {
                firebaseHelper.UpdateAcc(editedAcc);
                return RedirectToAction("Index", "Account");
            }

            // Nếu dữ liệu không hợp lệ, bạn có thể hiển thị biểu mẫu với thông báo lỗi
            return View(editedAcc);
        }

        //XÓA LOẠI TÀI KHOẢN
        [HttpGet]
        public IActionResult DeleteAcc(string deleteKey)
        {
            // Gọi hàm xóa loại tài khoản với key được truyền vào
            firebaseHelper.DeleteAcc(deleteKey);

            // Sau khi xóa, chuyển hướng về trang danh sách loại tài khoản hoặc trang khác tùy ý
            return RedirectToAction("Index", "Account");
        }
      //------------------------------------------------------------------------------------------------------------------

    }
}
