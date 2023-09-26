using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()

        {
            

            //    FirebaseResponse response  = await client.GetAsync("KhachHang");
            //Class a = response.ResultAs<Class>();
            //ViewData["j"] = a;
            return View();
        }
    }
}