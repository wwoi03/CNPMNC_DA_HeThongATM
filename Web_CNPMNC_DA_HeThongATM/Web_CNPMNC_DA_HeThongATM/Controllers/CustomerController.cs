using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CustomerController : Controller
    {

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public async Task<IActionResult> Index()
        {
            ViewData["j"] = await firebaseHelper.GetCustommers();
            return View();
        }

        //tạo khách hàng
        [HttpPost]
        public IActionResult CreateCustommer(KhachHangViewModel customer)
        {
            customer.MatKhau = firebaseHelper.PassRandom();
            ModelState.Remove("Makh");
            ModelState.Remove("MatKhau");
            if (ModelState.IsValid)
            {

                firebaseHelper.InsertCustommer(customer);

                return RedirectToAction("Index");
            }

            return View("Index", customer);

        }
        //check cccd
        [HttpGet]
        public async Task<IActionResult> CheckCanCuoc(string cccd)
        {

            var idcccd = await firebaseHelper.CheckCCCDExist(cccd);
            Console.WriteLine(idcccd);
            return Json(idcccd);
        }
    }
}
