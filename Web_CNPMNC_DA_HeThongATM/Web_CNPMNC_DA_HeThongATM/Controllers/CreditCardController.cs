using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class CreditCardController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public IActionResult Index()
        {
            return View();
        }
        [HttpGet]
        public IActionResult GetNameCus(string cccd)
        {
            string PIN = firebaseHelper.CreatePIN();
            string Stk = firebaseHelper.CreateAccountNumbet();
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cccd);
            if (custommer == null)
            {
                return Json("null");
            }
            var data = new
            {
                Tenkh = custommer.TenKhachHang,
                Sdt = custommer.SoDienThoai,
                PIN = PIN,
                Stk = Stk
            };
            return Json(data);
        }

        [HttpPost]
        public IActionResult CreateCard(TheNganHangViewModel cardViewModel)
        {
            //lấy khách hàng
            KhachHang custommer = firebaseHelper.GetCustomerbyid(cardViewModel.MaKH);
            if (cardViewModel == null || custommer == null) return RedirectToAction("Index");
            //đẩy lên database
            cardViewModel.MaDangNhap = "Nguyễn Lê Quốc Thuận";
            firebaseHelper.CreateCard(cardViewModel, cardViewModel.MaKH);
            //firebaseHelper.CreateCardLink(TaiKhoanLienKet.DefaultCard(cardViewModel.MaPIN, custommer.TenKhachHang, cardViewModel.MaSoThe));

            return RedirectToAction("Index");

        }

        //*******************************************
        string customerKey;
        string cardtypeKey;
        string tenKH;
        string masothe;
        TheNganHang theNganHang;
        public IActionResult AccountControl()
        {

            return View();
        }
       
        public IActionResult CardControl()
        {
            ViewBag.get = firebaseHelper.GetCardTypes();
            return View();
        }
        [HttpPost]
        public IActionResult sendCard([FromBody]inputDatacuaHao input)
        {
            string tinhtrang = "undefined";
            if (firebaseHelper.GetCustomerbyid(input.CCCD) != null && firebaseHelper.getCardTypeKeybyName(input.LoaiThe)!=null)
            {
                tenKH = firebaseHelper.GetCustomerbyid(input.CCCD).TenKhachHang;
                customerKey = firebaseHelper.GetKeysBycccd(input.CCCD);
                cardtypeKey = firebaseHelper.getCardTypeKeybyName(input.LoaiThe);
                theNganHang = firebaseHelper.getCardbyCusTypeKeys(customerKey, cardtypeKey);
                if (theNganHang != null)
                {
                    switch (theNganHang.TinhTrangThe)
                    {
                        case 0:
                            tinhtrang = "Hoạt động";
                            break;
                        case 1:
                            tinhtrang = "Người dùng đang";
                            break;
                        case 2:
                            tinhtrang = "Ngân hàng đang khóa";
                            break;
                    }
                }
            }
            else
            {
                return Json("Không tìm thấy");
            }
            CardControlViewModel cardInfor = new CardControlViewModel
            {
                theNganHang = theNganHang,
                CCCD = input.CCCD,
                LoaiThe = input.LoaiThe,
                tenKH = tenKH,
                TinhTrang = tinhtrang
            }; return Json(cardInfor);

        }
        [HttpPost]
        public IActionResult changeStatus([FromBody] inputDatacuaHao input)
        {
            if (firebaseHelper.GetCustomerbyid(input.CCCD) != null && firebaseHelper.getCardTypeKeybyName(input.LoaiThe) != null)
            {
                customerKey = firebaseHelper.GetKeysBycccd(input.CCCD);
                cardtypeKey = firebaseHelper.getCardTypeKeybyName(input.LoaiThe);
                theNganHang = firebaseHelper.getCardbyCusTypeKeys(customerKey, cardtypeKey);
                if(firebaseHelper.getCardbyCusTypeKeys(customerKey, cardtypeKey) != null)
                {
                    firebaseHelper.ChangeCardStatus(theNganHang.MaSoThe);
                    return RedirectToAction("CardControl");
                }
            }
            return sendCard(input);
        }
    }
}