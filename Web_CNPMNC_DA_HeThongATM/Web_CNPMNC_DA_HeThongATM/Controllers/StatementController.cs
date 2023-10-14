using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatementController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        public IActionResult CustomerStatement()
        {
            List<LichSuGiaoDichViewModel> LSDG = firebaseHelper.danhsachLSGD();
            ViewBag.LSDG = LSDG;
            return View();
        }

        [HttpPost]
        public IActionResult CustomerStatement(CustomerStatementViewModel cus)
        {
            if (ModelState.IsValid)
            {
                string cusKey = firebaseHelper.GetKeysBycccd(cus.cccd);
                long masothe = firebaseHelper.getCardbyCusKeys(cusKey).MaSoThe;
                long stk = firebaseHelper.getAccountbyCardKey(masothe).SoTaiKhoan;

                List<LichSuGiaoDichViewModel> LSDG = firebaseHelper.getLSGD(stk, DateTime.Parse(cus.fromDate) ,DateTime.Parse(cus.toDate));
                ViewBag.LSDG = LSDG;
                return View(cus);
            }
            return View(cus);
        }
    }
}
