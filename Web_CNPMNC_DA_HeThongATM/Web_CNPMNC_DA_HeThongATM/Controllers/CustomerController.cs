using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;
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
        public IActionResult CreateCustommer(KhachHangViewModel customer)
        {

            customer.Makh = firebaseHelper.CreateidCus();
            customer.MatKhau =  firebaseHelper.CreateidCus();
            ModelState.Remove("Makh");
            ModelState.Remove("MatKhau");
            if (ModelState.IsValid)
            {
                
                 firebaseHelper.InsertCustommer(customer);
                
                return RedirectToAction("Index");
            }
           
            return View("Index",customer);
           
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
