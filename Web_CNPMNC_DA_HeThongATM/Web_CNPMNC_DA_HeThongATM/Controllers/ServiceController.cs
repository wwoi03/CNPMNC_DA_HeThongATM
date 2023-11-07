using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Newtonsoft.Json;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ServiceController : Controller
    {
        FirebaseHelper firebaseHelper;

        public ServiceController()
        {
            firebaseHelper = new FirebaseHelper();
        }

        public IActionResult Index()
        {
            ViewBag.pageTitle = "Dịch Vụ";
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
                }

                if (!TryValidateModel(chucNangViewModel, nameof(chucNangViewModel)))
                {
                    if (ModelState.IsValid)
                    {

                    } 
                    else
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
                            ModelState.AddModelError(error.key.Split('.')[1], error.errorMessage);
                        }

                        return View(chucNangViewModel); 
                    }
                }
            }

            return View(chucNangViewModel);
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
                ChucNang chucNang = chucNangViewModel.ConvertClassModel();

                firebaseHelper.CreateService(chucNang);
            } else
            {
                // Chuyển đổi ChucNangViewModel thành chuỗi JSON và lưu vào TempData
                string serializedData = JsonConvert.SerializeObject(chucNangViewModel);
                TempData["chucNangViewModel"] = serializedData;
            }

            return RedirectToAction("Index");
        }

        public IActionResult Details(string key)
        {
            ChucNangViewModel chucNangViewModel = new ChucNangViewModel();
            chucNangViewModel.ConvertViewModel(firebaseHelper.GetServiceByKey(key));

            // Chuyển đổi ChucNangViewModel thành chuỗi JSON và lưu vào TempData
            string serializedData = JsonConvert.SerializeObject(chucNangViewModel);
            TempData["chucNangViewModel"] = serializedData;

            return RedirectToAction("Index");
        }

        public IActionResult Edit(string key)
        {
            ChucNangViewModel chucNangViewModel = new ChucNangViewModel();
            chucNangViewModel.ConvertViewModel(firebaseHelper.GetServiceByKey(key));

            // Chuyển đổi ChucNangViewModel thành chuỗi JSON và lưu vào TempData
            string serializedData = JsonConvert.SerializeObject(chucNangViewModel);
            TempData["chucNangViewModel"] = serializedData;

            return RedirectToAction("Index");
        }
    }
}
