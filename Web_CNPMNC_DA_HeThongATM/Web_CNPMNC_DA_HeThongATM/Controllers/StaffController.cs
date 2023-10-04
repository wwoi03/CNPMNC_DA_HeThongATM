using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StaffController : Controller
    {
        FirebaseHelper firebaseHelper;

        public StaffController()
        {
            firebaseHelper = new FirebaseHelper();
        }

        // Danh sách nhân viên
        public IActionResult Index()
        {
            return View();
        }

        // Thêm nhân viên
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

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
                NamSinh = nhanVienViewModel.NamSinh,
                TenNhanVien = nhanVienViewModel.Ten
            };

            firebaseHelper.CreateStaff(nhanVien);

            return RedirectToAction("Index", "Staff");
        }
    }
}
