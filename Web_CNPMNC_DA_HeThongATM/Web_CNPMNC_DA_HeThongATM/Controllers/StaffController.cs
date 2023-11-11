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
        public IActionResult Decentralization(string tittle)
        {
            ViewBag.pageTitle = "Phân quyền";
            ViewBag.titleAction =tittle;
            ViewBag.listRole = firebaseHelper.GetListRole();

            RoleViewModel role = new RoleViewModel();

            return View(role);
        }
        [HttpPost]
        public IActionResult CreateRole(RoleViewModel role)
        {
            if (ModelState.IsValid)
            {
                ChucVu chucVu = new ChucVu
                {
                    TenChucVu = role.name,
                    Key = role.Key
                };
                firebaseHelper.AddRole(chucVu);
            }

            return RedirectToAction("Index");

        }
        public void AddModelError()
        {
            var errorList = new List<(string key, string errorMessage)>();

            foreach (var key in ModelState.Keys)
            {
                var errors = ModelState[key].Errors;

                foreach (var error in errors)
                {
                    errorList.Add((key, error.ErrorMessage));
                }
            }

            foreach (var error in errorList)
            {
                if (error.key.Split('.').Length == 2)
                {
                    ModelState.AddModelError(error.key.Split('.')[1], error.errorMessage);
                }
            }
        }
        // Thêm nhân viên
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }
        
       
        //[HttpPost]
        //public IActionResult Create(NhanVienViewModel nhanVienViewModel)
        //{
        //    NhanVien nhanVien = new NhanVien()
        //    {
        //        ChiNhanh = nhanVienViewModel.ChiNhanh,
        //        ChucVu = nhanVienViewModel.ChucVu,
        //        DiaChi = nhanVienViewModel.DiaChi,
        //        Email = nhanVienViewModel.Email,
        //        MatKhau = nhanVienViewModel.MatKhau,
        //        NamSinh = nhanVienViewModel.NamSinh,
        //        TenNhanVien = nhanVienViewModel.Ten
        //    };

        //    firebaseHelper.CreateStaff(nhanVien);

        //    return RedirectToAction("Index", "Staff");
        //}
    }
}
