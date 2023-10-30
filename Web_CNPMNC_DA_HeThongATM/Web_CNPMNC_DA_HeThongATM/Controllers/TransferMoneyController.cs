using Microsoft.AspNetCore.Mvc;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class TransferMoneyController : Controller
    {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        public IActionResult Index()
        {

            return View();
        }
        [HttpPost]
        public IActionResult ChuyenTien(AccountViewModel account)
        {
            double sotien = account.SoTien;
            firebaseHelper.RutTien(sotien, account.SoTaiKhoan);
            return RedirectToAction("Index");
        }
        //public Account(Account firebaseClient)
        //{
        //    firebaseClient = firebaseClient;
        //}

        //[HttpGet]
        //public ActionResult<string> GetAccount(string value)
        //{
        //    string info = firebaseHelper.GetAccount(value);
        //    if (info != null)
        //    {
        //        return Ok(info); // Trả về thông tin tài khoản nếu tìm thấy
        //    }
        //    else
        //    {
        //        return NotFound("Không tìm thấy tài khoản"); // Trả về lỗi nếu không tìm thấy tài khoản
        //    }
        //}

        //[HttpPost]
        //public ActionResult<string> RutTien([FromBody] NapTienRequestModel model)
        //{
        //    if (model == null || string.IsNullOrEmpty(model.SoTaiKhoan) || model.SoTien <= 0)
        //    {
        //        return BadRequest("Dữ liệu không hợp lệ");
        //    }

        //    bool RutTienResult = firebaseHelper.NapTien(model.SoTien, model.SoTaiKhoan);

        //    if (RutTienResult)
        //    {
        //        return Ok("Rút tiền thành công");
        //    }
        //    else
        //    {
        //        return BadRequest("Rút tiền thất bại"); // Trả về lỗi nếu Rút tiền thất bại
        //    }
        //}
    }
}
