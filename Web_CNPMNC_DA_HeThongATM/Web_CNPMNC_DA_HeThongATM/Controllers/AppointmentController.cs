using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AppointmentController : Controller
    {
        FirebaseHelper firebaseHelper;

        public AppointmentController()
        {
            firebaseHelper = new FirebaseHelper();
        }
        public IActionResult Index()
        {
            List<DatLichHen> datLichHens = firebaseHelper.GetAppointent();
            List<DatLichHenViewModel> datLichHenViewModels = new List<DatLichHenViewModel>();
            foreach (var i in datLichHens)
            {
                var pro = new DatLichHenViewModel
                {
                    Key = i.Key,
                    SoDienThoai = i.SoDienThoai,
                    TenKhachHang = i.TenKhachHang,
                    LoaiDichVu = i.LoaiDichVu,
                    NgayDenHen = i.NgayDenHen,
                    TrangThai = i.TrangThai,
                };
                datLichHenViewModels.Add(pro);
            }
            ViewBag.listStatus = TrangThaiDatLichHenViewModel.DefaultStatus();
            ViewData["j"] = datLichHenViewModels;

            return View();
        }
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
        [HttpGet]
        public IActionResult DetailsLichHen(string Key)
        {

            DatLichHen datLichHen = firebaseHelper.GetAppointmentbyKey(Key);

            //ViewBag.TenKhachHang = firebaseHelper.GetNameCustomerbyid(Key);

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
            firebaseHelper.GetLichHenByKey(Key);
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
