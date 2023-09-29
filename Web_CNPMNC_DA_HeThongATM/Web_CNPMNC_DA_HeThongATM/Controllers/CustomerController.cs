using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CustomerController : Controller
    {
        FirebaseHelper firebaseHelper;

        public CustomerController()
        {
            firebaseHelper = new FirebaseHelper();
        }
        // Danh sách khách hàng
        public IActionResult Index()
        {
            ViewData["j"] =  firebaseHelper.GetCustommers();
            return View();
        }
        //tạo khách hàng
        [HttpPost]
        public async Task<IActionResult> CreateCard(KhachHangViewModel customer)
        {
            customer.Makh = await firebaseHelper.CreateidCus();
            await firebaseHelper.InsertCustommer(customer);
            return RedirectToAction("Index");
        }
    }
}
