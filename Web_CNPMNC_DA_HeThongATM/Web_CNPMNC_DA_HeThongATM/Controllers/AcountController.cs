using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Controllers.Data;
using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers
{
    public class AccountController : Controller
    {
        private readonly IRepository<LoaiTaiKhoan> _loaiTaiKhoanRepository;

        public AccountController(IRepository<LoaiTaiKhoan> loaiTaiKhoanRepository)
        {
            _loaiTaiKhoanRepository = loaiTaiKhoanRepository;
        }

        // Phương thức hiển thị danh sách loại tài khoản
        public IActionResult Index()
        {
            var danhSachLoaiTaiKhoan = _loaiTaiKhoanRepository.GetAll();
            return View(danhSachLoaiTaiKhoan);
        }

        // Phương thức hiển thị form tạo mới loại tài khoản
        public IActionResult Create()
        {
            return View();
        }

        // Phương thức xử lý tạo mới loại tài khoản
        [HttpPost]
        public IActionResult Create(LoaiTaiKhoan loaiTaiKhoan)
        {
            if (ModelState.IsValid)
            {
                _loaiTaiKhoanRepository.Insert(loaiTaiKhoan);
                return RedirectToAction("Index");
            }
            return View(loaiTaiKhoan);
        }

        // Phương thức hiển thị form chỉnh sửa loại tài khoản
        public IActionResult Edit(string key)
        {
            var loaiTaiKhoan = _loaiTaiKhoanRepository.GetById(key);
            if (loaiTaiKhoan == null)
            {
                return NotFound();
            }
            return View(loaiTaiKhoan);
        }

        // Phương thức xử lý chỉnh sửa loại tài khoản
        [HttpPost]
        public IActionResult Edit(string key, LoaiTaiKhoan loaiTaiKhoan)
        {
            if (key != loaiTaiKhoan.Key)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                _loaiTaiKhoanRepository.Update(loaiTaiKhoan);
                return RedirectToAction("Index");
            }
            return View(loaiTaiKhoan);
        }

        // Phương thức xóa loại tài khoản
        public IActionResult Delete(string key)
        {
            var loaiTaiKhoan = _loaiTaiKhoanRepository.GetById(key);
            if (loaiTaiKhoan == null)
            {
                return NotFound();
            }
            _loaiTaiKhoanRepository.Delete(key);
            return RedirectToAction("Index");
        }
    }
}