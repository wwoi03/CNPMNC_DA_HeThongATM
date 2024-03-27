using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Designpattern.Factorymethod;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ManageInterestRateController : Controller
    {
        private ILaiSuatFactory _laiSuatFactory;
        private FirebaseHelper firebaseHelper;

        public ManageInterestRateController(ILaiSuatFactory laiSuatFactory)
        {
            _laiSuatFactory = laiSuatFactory;
        }

        // Danh sách lãi suất
        public IActionResult Index()
        {
            List<LaiSuat> laiSuats = firebaseHelper.GetLaiSuats();
            List<LaiSuatViewModel> laiSuatViewModels = new List<LaiSuatViewModel>();
            foreach (var i in laiSuats)
            {
                var pro = new LaiSuatViewModel
                {
                    Key = i.Key,
                    KyHan = i.KyHan,
                    TiLe = i.TiLe
                };
                laiSuatViewModels.Add(pro);
            }

            ViewData["aben"] = laiSuatViewModels;

            return View();
        }


        // Tìm lãi suất
        //public IActionResult SearchLaiSuat(string searchLaiSuat)
        //{
        //    LaiSuat laiSuat = firebaseHelper.SearchLaiSuat(searchLaiSuat);
        //    LaiSuatViewModel ViewThes = new LaiSuatViewModel();

        //    if (laiSuat != null)
        //    {
        //        ViewThes.Key = laiSuat.Key;
        //        ViewThes.KyHan = laiSuat.KyHan;
        //        ViewThes.TiLe = laiSuat.TiLe;
        //    }

        //    return PartialView("SearchLaiSuat", ViewThes);
        //}

        // Tạo lãi suất
        
        [HttpPost]
        public IActionResult CreateLaiSuat()
        {
            var laiSuat = _laiSuatFactory.CreateLaiSuat();
            // Tiếp tục xử lý tạo mới lãi suất
            return View();
        }


        // Xác nhận xóa lãi suất
        public IActionResult ConfirmDelete(string key)
        {
            // Lấy thông tin lãi suất dựa trên key.
            LaiSuatViewModel laiSuat = firebaseHelper.GetLaiSuatByKey(key);

            if (laiSuat == null)
            {
                return View("LaiSuatNotFound");
            }

            return View("ConfirmDelete", laiSuat);
        }



        //Xóa lãi suất
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Delete(string key)
        {
            // Xóa lãi suất bằng key
            firebaseHelper.DeleteLaiSuat(key);
            return RedirectToAction("Index");
        }
        //sửa lãi suất
        public IActionResult EditLaiSuat(string key)
        {
            LaiSuatViewModel laiSuat = firebaseHelper.GetLaiSuatByKey(key);

            if (laiSuat == null)
            {
                return View("LaiSuatNotFound");
            }

            return View("EditLaiSuat", laiSuat);
        }

        [HttpPost]
        public IActionResult EditLaiSuat(LaiSuatViewModel updatedLaiSuat)
        {
            if (ModelState.IsValid)
            {
                firebaseHelper.UpdateLaiSuatByKey(updatedLaiSuat.Key, updatedLaiSuat);
                return RedirectToAction("Index");
            }

            return View("EditLaiSuat", updatedLaiSuat);
        }




    }
}