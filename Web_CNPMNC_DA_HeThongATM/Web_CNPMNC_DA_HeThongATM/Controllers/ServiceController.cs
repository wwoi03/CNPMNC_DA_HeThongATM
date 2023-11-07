using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

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
            @ViewBag.pageTitle = "Dịch Vụ";
            ViewBag.listService = firebaseHelper.GetListService();

            return View();
        }

        [HttpPost]
        public IActionResult Create(ChucNangViewModel chucNangViewModel)
        {
            if (ModelState.IsValid)
            {
                ChucNang chucNang = chucNangViewModel.ConvertClassModel();

                firebaseHelper.CreateService(chucNang);
            }
            return RedirectToAction("Index");
        }
    }
}
