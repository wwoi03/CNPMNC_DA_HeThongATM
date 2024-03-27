using Microsoft.AspNetCore.Mvc;
namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class NhanVien :ICloneable
	{
		public string Key { get; set; }
		public string ChiNhanhKey { get; set; }
		public string ChucVu { get; set; }
		public string DiaChi { get; set; }
		public string Email { get; set; }
		public string MatKhau { get; set; }
		public string NgaySinh { get; set; }
		public string GioiTinh { get; set; }
		public string SoDienThoai { get; set; }
		public string TenNhanVien { get; set; }
		// Thuộc tính lưu trữ bản sao của đối tượng NhanVien trước khi chỉnh sửa
		private NhanVien temporaryNhanVien;

		// Phương thức Clone()
		public object Clone()
		{
			// Tạo một bản sao của đối tượng NhanVien
			NhanVien clonedNhanVien = new NhanVien
			{
				Key = this.Key,
				ChiNhanhKey = this.ChiNhanhKey,
				ChucVu = this.ChucVu,
				DiaChi = this.DiaChi,
				Email = this.Email,
				MatKhau = this.MatKhau,
				NgaySinh = this.NgaySinh,
				GioiTinh = this.GioiTinh,
				SoDienThoai = this.SoDienThoai,
				TenNhanVien = this.TenNhanVien
			};

			// Lưu trữ bản sao của đối tượng NhanVien trước khi chỉnh sửa
			temporaryNhanVien = clonedNhanVien;

			// Trả về bản sao của đối tượng NhanVien
			return clonedNhanVien;
		}

		// Phương thức để lấy bản sao của đối tượng NhanVien trước khi chỉnh sửa
		public NhanVien GettemporaryNhanVien()
		{
			return temporaryNhanVien.Clone() as NhanVien;
		}
	}


}
