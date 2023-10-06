using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class TransferMoneyController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public IActionResult Index()
        {

            return View();
        }
        [HttpPost]
        public IActionResult ChuyenTien(AccountViewModel account)
        {
            double sotien = account.SoTien;
            firebaseHelper.RutTien(sotien, account.SoTaiKhoan);
            return RedirectToAction("Index");
        }
    }
}
