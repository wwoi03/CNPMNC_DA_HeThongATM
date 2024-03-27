using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Web_CNPMNC_DA_HeThongATM.Designpattern.Singleton;
using Web_CNPMNC_DA_HeThongATM.Designpattern.Strategy;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AppointmentController : Controller
    {
        FirebaseHelper firebaseHelper;
        private ListAppointMentStrategy appointMentStrategy;

        public AppointmentController()
        {
            appointMentStrategy = new IAppointmentStrategy();
        }
        public IActionResult Index()
        {
			var DatLichHen = appointMentStrategy.listAppointment();
			List<DatLichHenViewModel> datLichHenViewModels = new List<DatLichHenViewModel>();

            foreach (var i in DatLichHen)
            {
                var pro = new DatLichHenViewModel
                {
                    Key = i.Key,
                    SoDienThoai = i.SoDienThoai,
                    TenKhachHang = i.TenKhachHang,
                    LoaiDichVu = i.LoaiDichVu,
                    NgayDenHen = i.NgayDenHen.ToString("dd/MM/yyy"),
                    TrangThai = i.TrangThai,

                };
                datLichHenViewModels.Add(pro);
            }
            ViewBag.listStatus = TrangThaiDatLichHenViewModel.DefaultStatus();
            ViewData["j"] = datLichHenViewModels;

            return View();

        }
        //public IActionResult Index()
        //{
        //    List<DatLichHen> datLichHens = firebaseHelper.GetAppointent();
        //    List<DatLichHenViewModel> datLichHenViewModels = new List<DatLichHenViewModel>();

        //    foreach (var i in datLichHens)
        //    {
        //        var pro = new DatLichHenViewModel
        //        {
        //            Key = i.Key,
        //            SoDienThoai = i.SoDienThoai,
        //            TenKhachHang = i.TenKhachHang,
        //            LoaiDichVu = i.LoaiDichVu,
        //           NgayDenHen = i.NgayDenHen.ToString("dd/MM/yyy"),
        //            TrangThai = i.TrangThai,

        //        };
        //        datLichHenViewModels.Add(pro);
        //    }
        //    ViewBag.listStatus = TrangThaiDatLichHenViewModel.DefaultStatus();
        //    ViewData["j"] = datLichHenViewModels;

        //    return View();
        //}
        public IActionResult CreateLichHen()
        {
            return View();
        }
        [HttpPost]
        public IActionResult CreateLichHen(DatLichHenViewModel datLichHen)
        {
            datLichHen.TrangThai = 0;
            ModelState.Remove("TrangThai");
            ModelState.Remove("Key");
            if (ModelState.IsValid)
            {
                firebaseHelper.InsertAppointment(datLichHen);
                TempData["Message"] = "Đặt Lịch Hẹn";
                return RedirectToAction("Index");
            }
            TempData["Fail"] = "Đặt Lịch Hẹn";
            return View("CreateLichHen", datLichHen);

        }
        [HttpPost]
        public IActionResult CreateLichHenV2(DatLichHenViewModel datLichHen)
        {

            datLichHen.TrangThai = 0;
            ModelState.Remove("TrangThai");
            ModelState.Remove("Key"); 
            if (ModelState.IsValid)
            {
				FirebaseSingleton.GetInstance().InsertAppointment(datLichHen);
                TempData["Message"] = "Đặt Lịch Hẹn";
                return RedirectToAction("Index");
            }
            TempData["Fail"] = "Đặt Lịch Hẹn";
            return View("CreateLichHen", datLichHen);
        }
        [HttpGet]
        public IActionResult DetailsLichHen(string Key)
        {

            DatLichHen datLichHen = FirebaseSingleton.GetInstance().GetAppointmentbyKey(Key);


            ViewBag.Details = datLichHen;

            return View();

        }
        [HttpPost]

        public IActionResult DetailsCustomer(DatLichHenViewModel datLichHen)

        {

            return View();

        }
        public IActionResult ChinhTrangThai(string Key)
        {
            FirebaseSingleton.GetInstance().GetLichHenByKey(Key);
            return View();
        }

        [HttpPost]
        public IActionResult ChinhTrangThai(DatLichHenViewModel status)
        {
            firebaseHelper.TrangThai(status.TrangThai, status.Key);
            return RedirectToAction("Index");
        }
    }
}
