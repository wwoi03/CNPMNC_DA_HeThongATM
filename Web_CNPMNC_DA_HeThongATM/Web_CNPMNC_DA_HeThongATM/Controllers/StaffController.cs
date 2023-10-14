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
    public class StaffController : Controller
    {
        public static IFirebaseClient client;
        FirebaseHelper firebaseHelper;


        
        public StaffController()
        {
            firebaseHelper = new FirebaseHelper();
        }
     //--------------------------------------------------------NHÂN VIÊN--------------------------------------------------------
        // DANH SÁCH NHÂN VIÊN
        public IActionResult Index()
        {
            Dictionary<string, NhanVien> danhSachNhanVien = firebaseHelper.GetStaffsWithKey();
            ViewBag.danhSachNhanVien = danhSachNhanVien;

            return View(danhSachNhanVien);
        }

        // THÊM NHÂN VIÊN
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

        // HIỂN THỊ THÔNG TIN NHÂN VIÊN CẦN SỬA
        [HttpGet]
        public IActionResult Edit(String editKey)
        {
            Dictionary<string, NhanVien> danhSachNhanVien = firebaseHelper.GetStaffsWithKey();
            ViewBag.danhSachNhanVien = danhSachNhanVien;

            if (ViewBag.danhSachNhanVien.TryGetValue(editKey, out NhanVien nhanVien))
            {
                return View(nhanVien);
            }
            return View(danhSachNhanVien);
        }

        //SỬA NHÂN VIÊN
        [HttpPost]
        public IActionResult Edit(NhanVien editedNhanVien)
        {
            // Trích xuất thông tin từ biểu mẫu và cập nhật vào cơ sở dữ liệu
            if (ModelState.IsValid)
            {
                firebaseHelper.UpdateStaff(editedNhanVien);
                return RedirectToAction("Index", "Staff");
            }

            // Nếu dữ liệu không hợp lệ, bạn có thể hiển thị biểu mẫu với thông báo lỗi
            return View(editedNhanVien);
        }


        // TẠO NHÂN VIÊN
        [HttpPost]
        public IActionResult Create(NhanVienViewModel nhanVienViewModel)
        {
            NhanVien nhanVien = new NhanVien()
            {
                ChiNhanh = nhanVienViewModel.ChiNhanh,
                ChucVu = nhanVienViewModel.ChucVu,
                DiaChi = nhanVienViewModel.DiaChi,
                Email = nhanVienViewModel.Email,
                MatKhau = nhanVienViewModel.MatKhau,
                NgaySinh = nhanVienViewModel.NgaySinh,
                SoDienThoai = nhanVienViewModel.SoDienThoai,
                TenNhanVien = nhanVienViewModel.Ten
            };
            firebaseHelper.CreateStaff(nhanVien);
            return RedirectToAction("Index", "Staff");
        }
     //------------------------------------------------------------------------------------------------------------------------








      //--------------------------------------LOẠI TÀI KHOẢN--------------------------------------------

        public IActionResult Account()
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

        //TẠO LOẠI TÀI KHOẢN
        [HttpPost]
        public IActionResult CreateAcc(LoaiTaiKhoanViewModel taiKhoanViewModel)
        {
            LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan()
            {
                TenLoaiTaiKhoan = taiKhoanViewModel.TenLoaiTaiKhoan,
                
            };

            firebaseHelper.CreateAccount(loaiTaiKhoan);
            return RedirectToAction("Account", "Staff");
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

        //SỬA LOẠI TÀI KHOẢN
        [HttpPost]
        public IActionResult EditAcc(LoaiTaiKhoan editedAcc)
        {
            // Trích xuất thông tin từ biểu mẫu và cập nhật vào cơ sở dữ liệu
            if (ModelState.IsValid)
            {
                firebaseHelper.UpdateAcc(editedAcc);
                return RedirectToAction("Account", "Staff");
            }

            // Nếu dữ liệu không hợp lệ, bạn có thể hiển thị biểu mẫu với thông báo lỗi
            return View(editedAcc);
        }

        [HttpGet]
        public IActionResult DeleteAcc(string deleteKey)
        {
            // Gọi hàm xóa loại tài khoản với key được truyền vào
            firebaseHelper.DeleteAcc(deleteKey);

            // Sau khi xóa, chuyển hướng về trang danh sách loại tài khoản hoặc trang khác tùy ý
            return RedirectToAction("Account", "Staff");
        }

    }
}
