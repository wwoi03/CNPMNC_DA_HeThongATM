using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
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

            if (TempData.ContainsKey("roleMau"))
            {
                string serializedData = TempData["roleMau"] as string;

                if (!string.IsNullOrEmpty(serializedData))
                {
                    // Chuyển đổi JSON thành RoleViewModel
                    role = JsonConvert.DeserializeObject<RoleViewModel>(serializedData);

                    // Chạy lại xác thực
                    if (!TryValidateModel(role, nameof(role)))
                    {
                        if (ModelState.IsValid == false)
                        {
                            AddModelError();
                        }
                    }
                }
            }
            TempData.Clear();
            ChucVu chucVu = new ChucVu()
            {
                TenChucVu = role.name,
                Key = role.Key,
            };
                ViewBag.Check = firebaseHelper.checkRole(chucVu).Result.ToString();
            return View(role);
        }

        [HttpPost]
        public IActionResult CreateRole(RoleViewModel role)
        {
            ModelState.Remove("Key");
            if (ModelState.IsValid)
            {
                ChucVu chucVu = new ChucVu()
                {
                    TenChucVu = role.name,
                    Key = role.Key,
                };
                if (!firebaseHelper.checkRole(chucVu).Result)
                {
                    firebaseHelper.AddRole(chucVu);
                }
               
            }
            else
            {
                TempData["roleMau"] = JsonConvert.SerializeObject(role);
            }
            return RedirectToAction("Decentralization");

        }

        public IActionResult DetailsRole(string key)
        {
            RoleViewModel role = new RoleViewModel()
            {
                Key = firebaseHelper.getRolebyKey(key).Key,
                name = firebaseHelper.getRolebyKey(key).TenChucVu,
            };
            TempData["roleMau"] = JsonConvert.SerializeObject(role);
            return RedirectToAction("Decentralization", new { title = "Chi tiết chức vụ" });

        }

        public IActionResult EditRole(string key)
        {
            RoleViewModel role = new RoleViewModel()
            {
              Key=  firebaseHelper.getRolebyKey(key).Key,
              name = firebaseHelper.getRolebyKey(key).TenChucVu,
            };
            TempData["roleMau"] = JsonConvert.SerializeObject(role);
            return RedirectToAction("Decentralization", new { title = "Chỉnh sửa chức vụ" });

        }
        [HttpPost]
        public IActionResult EditRole(RoleViewModel role)
        {
            ModelState.Remove("Key");
            if (ModelState.IsValid)
            {
                ChucVu chucVu = new ChucVu()
                {
                    Key = role.Key,
                    TenChucVu = role.name,
                };
                if (!firebaseHelper.checkRole(chucVu).Result)
                    firebaseHelper.EditRole(chucVu);
            }
            else
            {
                TempData["roleMau"] = JsonConvert.SerializeObject(role);
            }

            return RedirectToAction("Decentralization", new { title = "Chỉnh sửa chức vụ" });

        }
        public IActionResult DeleteRole(string key)
        {
            firebaseHelper.DeleteRole(key);
            return RedirectToAction("Decentralization");
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
