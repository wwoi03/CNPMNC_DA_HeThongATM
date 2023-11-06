
﻿using FireSharp;
using Microsoft.AspNetCore.Mvc;
﻿using Microsoft.AspNetCore.Mvc;

using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AccountStatusController : Controller
    {
        string TenTK;
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public IActionResult Index()
        {
            return View();
        }
        [HttpGet]
        public IActionResult sendAccount(long SoTaiKhoan)
        {
            string tinhtrang = "undefined";
            if (firebaseHelper.GetAccountbyid(SoTaiKhoan) != null)
            {
                TenTK = firebaseHelper.GetAccountbyid(SoTaiKhoan).TenTK;
                return Json(TenTK);
            }
            else
            {
                return Json("Không tìm thấy");
            }
        }

        //[HttpPost]
        //public IActionResult TinhTrangTaiKhoan(TaiKhoanLienKetViewModel account)
        //{
        //    int tinhtrangTK = account.TinhTrangTaiKhoan;
        //    firebaseHelper.TinhTrangTaiKhoan(tinhtrangTK, account.TinhTrangTaiKhoan);
        //    return RedirectToAction("Index");
        //}

        [HttpPost]
        public IActionResult TrangThaiTk(long SoTaiKhoan, int tinhTrangTaiKhoan)
        {
            if (firebaseHelper.TrangThaiTk(SoTaiKhoan, tinhTrangTaiKhoan))
            {
                return RedirectToAction("Index");
            }
            else
            {
                // Xử lý khi thay đổi trạng thái không thành công
                return View("Error");
            }
        }

       
    }
}
