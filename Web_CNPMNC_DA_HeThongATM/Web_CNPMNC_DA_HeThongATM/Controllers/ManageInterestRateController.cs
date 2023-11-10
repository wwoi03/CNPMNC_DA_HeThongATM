using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ManageInterestRateController : Controller
    {
       

        FirebaseHelper firebaseHelper;

        public ManageInterestRateController()
        {
            firebaseHelper = new FirebaseHelper();
        }


        // Danh sách Lãi suất
        //public IActionResult Index()
        //{
        //    List<LaiSuat> laiSuats = firebaseHelper.GetLaiSuats();
        //    List<LaiSuatViewModel> LaiSuatViewModel = new List<LaiSuatViewModel>();

        //    foreach (var i in laiSuats)
        //    {
        //        var pro = new LaiSuatViewModel
        //        {
        //            Key = i.Key,
        //            KyHan = i.KyHan,
        //            TiLe = i.TiLe,
        //        };
        //        LaiSuatViewModel.Add(pro);
        //    }

        //    ViewData["LaiSuatList"] = LaiSuatViewModel;

        //    return View();
        //}
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

        //tạo lãi suất
        public IActionResult CreateLaiSuat()
        {
            return View();
        }

        [HttpPost]
        public IActionResult CreateLaiSuat(LaiSuatViewModel laiSuat)
        {

            /* loaiThe.MaLoaiTNH = firebaseHelper.PassRandom();
             ModelState.Remove("loaiThe");*/
            if (ModelState.IsValid)
            {

                firebaseHelper.InsertLaiSuats(laiSuat);

                return RedirectToAction("Index");
            }

            return View("CreateLaiSuat", laiSuat);

        }

        // Chỉnh sửa
        public IActionResult EditLaiSuat(string key)
        {
            // lấy thông tin lãi suất dựa trên key.
            LaiSuatViewModel laiSuat = firebaseHelper.GetLaiSuatByKey(key);

            if (laiSuat == null)
            {
                return View("LaiSuatNotFound");
            }

            return View("Index", laiSuat);
        }

        [HttpPost]
        public IActionResult EditLaiSuatPost(LaiSuatViewModel updatedLaiSuat)
        {
            if (ModelState.IsValid)
            {
                // cập nhật thông tin lãi suất bằng Key.
                firebaseHelper.UpdateLaiSuatByKey(updatedLaiSuat.Key, updatedLaiSuat);

                return RedirectToAction("Index");
            }

            return View("Index", updatedLaiSuat);
        }

        //Xóa lãi suất
        
        public IActionResult ConfirmDelete(string key)
        {
            // lấy thông tin lãi suất dựa = key.
            LaiSuatViewModel laiSuat = firebaseHelper.GetLaiSuatByKey(key);

            if (laiSuat == null)
            {
                return View("LaiSuatNotFound");
            }

            return View("Index", laiSuat);
        }

       
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Delete(string key)
        {
            // Xóa lãi suất bằng key
            firebaseHelper.DeleteLaiSuat(key);
            return RedirectToAction("Index");
        }



    }
}
