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
        //chỉnh sửa loại thẻ
        [HttpGet]
        public IActionResult EditTypesCard(long MaLoaiTNH)
        {

            LoaiThe loaiThe = firebaseHelper.GetMaLoaiTNH(MaLoaiTNH);
            ViewBag.TenKhachHang = firebaseHelper.GetMaLoaiTNH(MaLoaiTNH);
            ViewBag.Details = loaiThe;
            return View();
        }
        //Chỉnh sửa loại thẻ {nhận dữ liệu}
        [HttpPost]
        public IActionResult EditTypesCard(LoaiTheViewModel theNganHang)
        {
            return View();
        }




        //Xóa Loại Thẻ
        [HttpGet]
        public IActionResult DeleteTypesCard(long MaLoaiTNH)
        {
            LoaiThe loaiThe = firebaseHelper.GetMaLoaiTNH(MaLoaiTNH);
            ViewBag.TenKhachHang = firebaseHelper.GetMaLoaiTNH(MaLoaiTNH);
            ViewBag.Details = loaiThe;
            return View();
        }

    }
}

