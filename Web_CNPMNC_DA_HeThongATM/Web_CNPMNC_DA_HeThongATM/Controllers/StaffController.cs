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

        //LOGIN VIEW
        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }

        //XỬ LÝ LOGIN
        [HttpPost]
        public IActionResult Login(NhanVienViewModel nhanVienViewModel)
        {
            Dictionary<string, NhanVien> danhSachNhanVien = firebaseHelper.GetStaffsWithKey();
            ViewBag.danhSachNhanVien = danhSachNhanVien;

            // Kiểm tra xem danh sách có dữ liệu hay không
            if (danhSachNhanVien?.Any() == true)
            {
                // Lấy thông tin đăng nhập từ người dùng
                string enteredEmail = nhanVienViewModel.Email;
                string enteredPass = nhanVienViewModel.MatKhau;

                // Kiểm tra đăng nhập trong danh sách nhân viên
                foreach (var entry in danhSachNhanVien)
                {
                    string userEmailFromFirebase = entry.Value.Email;
                    string userPasswordFromFirebase = entry.Value.MatKhau;

                    // Nếu tìm thấy email và mật khẩu trùng khớp trong danh sách, đăng nhập thành công
                    if (enteredEmail == userEmailFromFirebase && enteredPass == userPasswordFromFirebase)
                    {
                        HttpContext.Session.SetString("NhanVienID", entry.Key);
                        HttpContext.Session.SetString("TenNhanVien", entry.Value.TenNhanVien);
                        HttpContext.Session.SetString("TenNhanVien", entry.Value.Email);

                        return RedirectToAction("Index", "Statistical"); 
                    }
                }
            }
            return RedirectToAction("Login", "Staff");
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

        [HttpGet]
        public IActionResult DeleteStaff(string deleteKey)
        {
            // Gọi hàm xóa loại tài khoản với key được truyền vào
            firebaseHelper.DeleteStaff(deleteKey);

            // Sau khi xóa, chuyển hướng về trang danh sách loại tài khoản hoặc trang khác tùy ý
            return RedirectToAction("Index", "Staff");
        }

        // TẠO NHÂN VIÊN
        [HttpPost]
        public IActionResult Create(NhanVienViewModel nhanVienViewModel)
        {
            NhanVien nhanVien = new NhanVien()
            {
                ChiNhanhKey = nhanVienViewModel.ChiNhanh,
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
    }
}
