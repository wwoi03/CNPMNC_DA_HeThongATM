using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatementController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        public IActionResult BankStatement()
        {
            //List<ThongKeViewModel> LSDG = firebaseHelper.danhsachLSGD();
            //ViewBag.LSGD = LSDG;
            ViewBag.check = "ok";
            return View();
        }

        public IActionResult CustomerStatement()
        {
            //TuanDao 0 cho
            //List<LichSuGiaoDichViewModel> LSDG = firebaseHelper.danhsachLSGD();
            List<GiaoDichViewModel> LSDG = new List<GiaoDichViewModel>();
            ViewBag.LSGD = LSDG;
            ViewBag.check = "ok";
            CustomerStatementViewModel cus = new CustomerStatementViewModel();
            cus.toDate = DateTime.Now.ToString("yyyy-MM-dd");
            cus.fromDate = DateTime.Now.ToString("yyyy-MM-dd");
            return View(cus);
        }

        [HttpPost]
        public IActionResult CustomerStatement(CustomerStatementViewModel cus)
        {

            if (ModelState.IsValid)
            {

                if (firebaseHelper.GetCustomerbyid(cus.cccd) != null)
                {
                    string cusKey = firebaseHelper.GetKeysBycccd(cus.cccd);
                    long stk = firebaseHelper.getAccountbyCusKey(cusKey).SoTaiKhoan;
                    List<GiaoDichViewModel> LSDG = firebaseHelper.getLSGD(stk, DateTime.Parse(cus.fromDate), DateTime.Parse(cus.toDate));
                    ViewBag.LSGD = LSDG;
                    ViewBag.check = "ok";
                }
                else
                {
                    List<GiaoDichViewModel> LSDG = new List<GiaoDichViewModel>();
                    ViewBag.LSDG = LSDG;
                    ViewBag.check = "undefined";
                    return View(cus);
                }
                return View(cus);
            }
            return View(cus);
        }


    }
}