using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StatisticalController : Controller
    {
        FirebaseHelper firebaseHelper;

        public StatisticalController()
        {
            firebaseHelper = new FirebaseHelper();
        }

        // Trang chính
        public IActionResult Index()
        {
            // Lấy danh sách khách hàng
            ViewBag.customers = firebaseHelper.GetCustomers();

            // Tính tiền ngân hàng
            ViewBag.totalAssets = firebaseHelper.GetTotalAssets();

            // Tính tổng số lượng giao dịch
            ViewBag.totalTransaction = firebaseHelper.GetTotalTransaction();

            // Lấy danh sách nhân viên
            ViewBag.staffs = firebaseHelper.GetStaffs();

            return View();
        }
    }
}
