using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models;

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
        public IActionResult Index()
        {
            List<LaiSuat> khachHangs = firebaseHelper.GetLaiSuats();
            List<LaiSuatViewModel> LaiSuatViewModel = new List<LaiSuatViewModel>();
            foreach (var i in khachHangs)
            {
                var pro = new LaiSuatViewModel
                {
                    Key = i.Key,
                    KyHan = i.KyHan,
                    TiLe = i.TiLe,
                };
                LaiSuatViewModel.Add(pro);
            }

            ViewData["LaiSuatList"] = LaiSuatViewModel;

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
    }
}
