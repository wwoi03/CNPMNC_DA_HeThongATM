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

        
        public IActionResult Login() //TRANG ĐĂNG NHẬP
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
            //  String path = "NhanVien";

            FirebaseResponse response =  client.Push("NhanVien", nhanVienViewModel);
            return RedirectToAction("Index");
        }


        [HttpPost]
        public IActionResult Employeeinfo(NhanVien nhanVien) // Nhận dữ liệu nhập vào
        {
            try
            {
                AddNhanVienToFireBase(nhanVien);
                ModelState.AddModelError(String.Empty,"Added Successfully");
            }
            catch(Exception ex)
            {
                ModelState.AddModelError(String.Empty, ex.Message);
            }
            return View(nhanVien);
        }


        private void AddNhanVienToFireBase(NhanVien nhanVien) //Thêm NhanVien vào firebase
        {
            client = new FireSharp.FirebaseClient(config);
          //  String path = "NhanVien";
            
            PushResponse response = client.Push("NhanVien",nhanVien);
        }





        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

    }
}