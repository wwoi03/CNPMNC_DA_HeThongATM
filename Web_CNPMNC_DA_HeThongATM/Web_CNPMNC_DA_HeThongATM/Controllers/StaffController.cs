using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using Web_CNPMNC_DA_HeThongATM.Models;
using FireSharp.Interfaces;
using FireSharp.Config;
using FireSharp.Response;
using FireSharp;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class StaffController : Controller
    {
        private readonly ILogger<StaffController> _logger;

        public StaffController(ILogger<StaffController> logger)
        {
            _logger = logger;
        }

        IFirebaseClient client;

        private IFirebaseConfig config = new FirebaseConfig
        {
            AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
            BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
        };


        public IActionResult Index() // TRANG HOME
        {
            return View();
        }

        [HttpGet]
        public IActionResult EmployeeSignUp() //EmployeeSignUp view
        {
            return View();
        }

        [HttpPost]
        public IActionResult EmployeeCreate(NhanVienViewModel nhanVienViewModel) //EmployeeSignUp view
        {
            client = new FirebaseClient(config);
            FirebaseResponse response =  client.Push("NhanVien", nhanVienViewModel);
            return RedirectToAction("Index");
        }
//-------------------------------------------------------------------------------------------------------------------

        [HttpGet]
        public IActionResult Login() //Login view
        {
            return View();
        }

        [HttpPost]
        public IActionResult CheckLogin(NhanVienViewModel nhanVienViewModel) 
        {
            client = new FirebaseClient(config);

            // Sử dụng FirebaseClient để lấy dữ liệu từ Firebase
            FirebaseResponse response = client.Get("NhanVien");

            // Kiểm tra xem có lỗi hay không khi tải dữ liệu
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                
                Dictionary<string, NhanVienViewModel> firebaseData = response.ResultAs<Dictionary<string, NhanVienViewModel>>();

                // LẤY DỮ LIỆU TỪ NGƯỜI DÙNG
                String enteredEmail = nhanVienViewModel.Email;
                String enteredPass = nhanVienViewModel.MatKhau;

                if (firebaseData != null && firebaseData.Count > 0)
                {
                    foreach (var entry in firebaseData)
                    {
                        // LẤY DỮ LIỆU TỪ FIREBASE
                        string userEmailFromFirebase = entry.Value.Email;
                        string userPasswordFromFirebase = entry.Value.MatKhau;

                        if (enteredEmail == userEmailFromFirebase && enteredPass == userPasswordFromFirebase)
                        {
                            return View("Login");
                        }
                    }
                }
            }
          
            return RedirectToAction("Index");
        }








        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

    }
}