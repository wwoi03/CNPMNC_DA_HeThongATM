using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Designpattern.CommandPattern;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ServiceController : Controller
    {
        FirebaseHelper firebaseHelper;
        private readonly FirebaseHelper _firebaseHelper;

        public ServiceController(FirebaseHelper firebaseHelper)
        {
            firebaseHelper = new FirebaseHelper();
            _firebaseHelper = firebaseHelper;
        }

        public IActionResult Index(string titleAction)
        {
            ViewBag.pageTitle = "Dịch Vụ";
            ViewBag.titleAction = titleAction;

            // lấy danh sách dịch vụ
            ViewBag.listService = firebaseHelper.GetListService();

            ChucNangViewModel chucNangViewModel = new ChucNangViewModel();

            // Kiểm tra xem TempData có chứa dữ liệu không
            if (TempData.ContainsKey("chucNangViewModel"))
            {
                string serializedData = TempData["chucNangViewModel"] as string;

                if (!string.IsNullOrEmpty(serializedData))
                {
                    // Chuyển đổi JSON thành ChucNangViewModel
                    chucNangViewModel = JsonConvert.DeserializeObject<ChucNangViewModel>(serializedData);

                    // Chạy lại xác thực
                    if (!TryValidateModel(chucNangViewModel, nameof(chucNangViewModel)))
                    {
                        if (ModelState.IsValid == false)
                        {
                            AddModelError();
                        }
                    }
                }
            }

            TempData.Clear();

            return View(chucNangViewModel);
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

        // Thêm dịch vụ
        [HttpPost]
        public IActionResult Create(ChucNangViewModel chucNangViewModel)
        {
            ModelState.Remove("Key");
            ModelState.Remove("ImageFile");
            ModelState.Remove("IconChucNang");

            if (ModelState.IsValid)
            {
                ChucNang chucNang = chucNangViewModel.ConvertClassModel(); // Chuyển đổi từ ViewModel sang Model
                ICommand createCommand = new CreateServiceCommand(_firebaseHelper, chucNang);
                CommandInvoker commandInvoker = new CommandInvoker(createCommand);
                commandInvoker.ExecuteCommand();

                return RedirectToAction("Index");
            }
            else
            {
                // Xử lý khi ModelState không hợp lệ
                return View(chucNangViewModel);
            }
        }

        // Hiển thị chi tiết dịch vụ
        public IActionResult Details(string key)
        {
            GetServiceByKey(key);

            return RedirectToAction("Index", new { titleAction = "Chi tiết dịch vụ" });
        }

        // Sửa dịch vụ
        public IActionResult Edit(string key)
        {
            GetServiceByKey(key);

            return RedirectToAction("Index", new { titleAction = "Chỉnh sửa dịch vụ" });
        }

        [HttpPost]
        public IActionResult Edit(ChucNangViewModel chucNangViewModel)
        {
            ModelState.Remove("Key");
            ModelState.Remove("ImageFile");
            ModelState.Remove("IconChucNang");

            if (ModelState.IsValid)
            {
                ChucNang chucNang = chucNangViewModel.ConvertClassModel();

                firebaseHelper.UpdateService(chucNang);
            } else
            {
                // Chuyển đổi ChucNangViewModel thành chuỗi JSON và lưu vào TempData
                TempData["chucNangViewModel"] = JsonConvert.SerializeObject(chucNangViewModel);
            }

            return RedirectToAction("Index", new { titleAction = "Chỉnh sửa dịch vụ" });
        }

        // Lấy dịch vụ theo key
        public void GetServiceByKey(string key)
        {
            ChucNangViewModel chucNangViewModel = new ChucNangViewModel();
            chucNangViewModel.ConvertViewModel(firebaseHelper.GetServiceByKey(key));

            // Chuyển đổi ChucNangViewModel thành chuỗi JSON và lưu vào TempData
            TempData["chucNangViewModel"] = JsonConvert.SerializeObject(chucNangViewModel);
        }
    }
}
