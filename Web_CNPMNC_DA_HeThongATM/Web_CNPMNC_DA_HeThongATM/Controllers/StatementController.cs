using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualBasic;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatementController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();



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
                }
                return RedirectToAction("CustomerStatement");
            }
            return View(cus);
        }
        // bank
        public IActionResult BankStatement()
        {
            ViewBag.year = DateTime.Now.Year;
            ViewBag.month = DateTime.Now.Month;
            ViewBag.count = firebaseHelper.CountGiaoDichMonth(DateTime.Now.Year, DateTime.Now.Month);
            if (TempData.ContainsKey("year") && TempData.ContainsKey("month"))
            {
                BankStatement bank = new BankStatement()
                {
                    year = int.Parse(TempData.Peek("year").ToString()),
                    month = int.Parse(TempData.Peek("month").ToString()),
                };
                ViewBag.year = bank.year;
                ViewBag.month = bank.month;
            }
            if (TempData.ContainsKey("count"))
            {
                ViewBag.count = TempData["count"];
            }
        

            DateTime.DaysInMonth(DateTime.Now.Year, DateTime.Now.Month);
            return View();
        }

        [HttpPost]
        public IActionResult Count(BankStatement bankStatement)
        {

            if (ModelState.IsValid)
            {
                TempData["year"] = bankStatement.year;
                TempData["month"] = bankStatement.month;
                TempData["count"] = firebaseHelper.CountGiaoDichMonth(bankStatement.year, bankStatement.month);
             
              
                return RedirectToAction("BankStatement");

            }
            return View(bankStatement);

        }
        [HttpGet]
        public IActionResult Money()
        {

            if (TempData.ContainsKey("year") && TempData.ContainsKey("month"))
            {
                BankStatement bank2 = new BankStatement()
                {
                    year = int.Parse(TempData.Peek("year").ToString()),
                    month = int.Parse(TempData.Peek("month").ToString()),
                };
                var moneyin2 = firebaseHelper.Money(bank2);
               
                return Json(moneyin2);
            }
         
            BankStatement bank = new BankStatement()
            {
                year = DateTime.Now.Year,
                month = DateTime.Now.Month,
            };
            var moneyin = firebaseHelper.Money(bank);
           
            return Json(moneyin);
        }
        //[HttpGet]
        //public IActionResult MoneyOut()
        //{

        //    if (TempData.ContainsKey("year") && TempData.ContainsKey("month"))
        //    {
        //        BankStatement bank2 = new BankStatement()
        //        {
        //            year = int.Parse(TempData.Peek("year").ToString()),
        //            month = int.Parse(TempData.Peek("month").ToString()),
        //        };
        //        var moneyout2 = firebaseHelper.MoneyOut(bank2);

        //        return Json(moneyout2);
        //    }

        //    BankStatement bank = new BankStatement()
        //    {
        //        year = DateTime.Now.Year,
        //        month = DateTime.Now.Month,
        //    };
        //    var moneyout = firebaseHelper.MoneyOut(bank);
        //    return Json(moneyout);
        //}


        //public class ThongKe
        //{
        //    public object MNI { get; set; }
        //    public object MNO { get; set; }
        //}
        //[HttpGet]
        //public IActionResult CountYear(int year)
        //{


        //        return Json(year);



        //}
        //[HttpGet]
        //public IActionResult CountMonth(BankStatement bankStatement)
        //{


        //        return Json(firebaseHelper.CountGiaoDichMonth(bankStatement.year,bankStatement.month));



        //}
        //[HttpPost]
        //public IActionResult Count(int year)
        //{

        //    if (ModelState.IsValid)
        //    {

        //        HttpContext.Session.SetString("count", year.ToString());
        //        return RedirectToAction("BankStatement");

        //    }
        //    return View();

        //}
    }
}