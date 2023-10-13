﻿using Firebase.Auth;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

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
            List<KhachHang> khachHangs = firebaseHelper.GetCustomers();
            List<KhachHangViewModel> khachHangViewModels = new List<KhachHangViewModel>();
            foreach(var i in khachHangs)
            {
                var pro = new KhachHangViewModel
                {
                    CCCD = i.CCCD,
                    DiaChi = i.DiaChi,
                    Email = i.Email,
                    GioiTinh = i.GioiTinh,
                    Sdt = i.Sdt,
                    TenKh = i.TenKh,
                    NgayTao = i.NgayTao,
                    MatKhau = i.MatKhau,

                };
                khachHangViewModels.Add(pro);


            }

            ViewData["j"] = khachHangViewModels;

            return View();
        }
        public IActionResult CreateCustommer()
        { 
        return View();
        }
        //tạo khách hàng
        [HttpPost]
        public IActionResult CreateCustommer(KhachHangViewModel customer)
        {
            customer.MatKhau = firebaseHelper.PassRandom();
            ModelState.Remove("Makh");
            ModelState.Remove("MatKhau");
            if (ModelState.IsValid)
            {

                firebaseHelper.InsertCustommer(customer);

                return RedirectToAction("Index");
            }

            return View("CreateCustommer", customer);

        }
        //check cccd
        [HttpGet]
        public async Task<IActionResult> CheckCanCuoc(string cccd)
        {

            var idcccd = await firebaseHelper.CheckCCCDExist(cccd);
            Console.WriteLine(idcccd);
            return Json(idcccd);
        }

        //check sdt
        [HttpGet]
        public IActionResult CheckSdt(string Sdt)
        {
          bool CheckSdt =  firebaseHelper.CheckSdt(Sdt);

            return Json(CheckSdt);
        }
        public ActionResult TimKiem(string tenKhachHang)
        {
            var khachHangs = firebaseHelper.SearchKhachHangByTen(tenKhachHang);
            return View(khachHangs);
        }
    }
}






