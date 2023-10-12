using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class ManageCardTypesController : Controller
    {
        FirebaseHelper firebaseHelper;

        public ManageCardTypesController()
        {
            firebaseHelper = new FirebaseHelper();
        }
        // Danh sách loại thẻ
        public IActionResult Index()
        {
            List<LoaiThe> khachHangs = firebaseHelper.GetTypesCards();
            List<LoaiTheViewModel> loaiTheViewModels = new List<LoaiTheViewModel>();
            foreach (var i in khachHangs)
            {
                var pro = new LoaiTheViewModel
                {
                   HanMucLoaiThe = i.HanMucLoaiThe,
                   MaLoaiTNH = i.MaLoaiTNH,
                   TenTNH = i.TenTNH,
                };
                loaiTheViewModels.Add(pro);
            }

            ViewData["TypesCardList"] = loaiTheViewModels;

            return View();
        }
        public IActionResult CreateTypesCard()
        {
            return View();
        }
        //tạo loại thẻ
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

