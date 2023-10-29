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

        // Danh sách loại thẻ
        public IActionResult Index()
        {
            List<LaiSuat> khachHangs = firebaseHelper.GetLaiSuat();
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
        //tạo loại thẻ
        public IActionResult CreateTypesCard()
        {
            return View();
        }

        [HttpPost]
        public IActionResult CreateTypesCard(LoaiTheViewModel loaiThe)
        {

            /* loaiThe.MaLoaiTNH = firebaseHelper.PassRandom();
             ModelState.Remove("loaiThe");*/
            if (ModelState.IsValid)
            {

                firebaseHelper.InsertTypesCards(loaiThe);

                return RedirectToAction("Index");
            }

            return View("CreateTypesCard", loaiThe);

        }
    }
}
