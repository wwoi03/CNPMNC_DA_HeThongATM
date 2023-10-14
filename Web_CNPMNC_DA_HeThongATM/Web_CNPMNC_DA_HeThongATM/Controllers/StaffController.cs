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

        // Danh sách nhân viên
        public IActionResult Index()
        {
            Dictionary<string, NhanVien> danhSachNhanVien = firebaseHelper.GetStaffsWithKey();
            ViewBag.danhSachNhanVien = danhSachNhanVien;

            return View(danhSachNhanVien);
        }

        // Thêm nhân viên
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

        // sửa nhân viên
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
