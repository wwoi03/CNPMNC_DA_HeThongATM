using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class TaiKhoanLienKet
    {
        public double HanMucTK { get; set; }

        public string Key { get; set; }

        public string LoaiTaiKhoan { get; set; }

        public string MaKHKey { get; set; }

        public string MaLoaiTKKey { get; set; }

        public string NgayGD {  get; set; }

        public double SoDu { get; set; }

        public long SoTaiKhoan { get; set; }

        public string TenTK {  get; set; }

        public double TienDaGD { get; set; }

        public double TienGD1Lan { get; set; }

        public int TinhTrangTaiKhoan { get; set; }
        

        public static TaiKhoanLienKet DefaultCard(TheNganHangViewModel theNganHang,KhachHang khachHang)
        {
            TaiKhoanLienKet taiKhoanLienKet = new TaiKhoanLienKet();

            taiKhoanLienKet.HanMucTK = 1000000000;
            taiKhoanLienKet.Key = "";
            taiKhoanLienKet.LoaiTaiKhoan = "adoasdlkghqw";
            taiKhoanLienKet.MaKHKey = khachHang.Key;
            taiKhoanLienKet.MaLoaiTKKey = "adoasdlkghqw";
            taiKhoanLienKet.NgayGD = "";
            taiKhoanLienKet.SoDu = 50000;
            taiKhoanLienKet.SoTaiKhoan = theNganHang.MaSoThe;
            taiKhoanLienKet.TienDaGD = 10;          
            taiKhoanLienKet.TienGD1Lan = 0;
            taiKhoanLienKet.TenTK = khachHang.TenKH;                     
            taiKhoanLienKet.TinhTrangTaiKhoan = 1;
            return taiKhoanLienKet;
        }





    }
}