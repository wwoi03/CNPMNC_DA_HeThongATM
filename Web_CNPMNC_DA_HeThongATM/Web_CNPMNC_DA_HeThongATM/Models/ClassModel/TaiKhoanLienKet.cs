using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class TaiKhoanLienKet
    {
        public string Key { get; set; }
        public string MaKHKey { get; set; }
        public string MaLoaiTKKey { get; set; }
        public long SoTaiKhoan { get; set; }
        public double SoDu { get; set; }
        public string TenTK { get; set; }
        public int TinhTrangTaiKhoan { get; set; }
        public double HanMucTK { get; set; }

        public static TaiKhoanLienKet DefaultCard(string Stk, string TenTaiKhoan, long MaSoThe)
        {
            TaiKhoanLienKet taiKhoanLienKet = new TaiKhoanLienKet();
            taiKhoanLienKet.SoDu = 50000;
            //taiKhoanLienKet.NgayGD = "abc";
            taiKhoanLienKet.SoTaiKhoan = long.Parse(Stk);
            //taiKhoanLienKet.TienDaGD = 0;
            //taiKhoanLienKet.TienGD1Lan = 0;
            taiKhoanLienKet.TinhTrangTaiKhoan = 1;
            taiKhoanLienKet.TenTK = TenTaiKhoan;
            taiKhoanLienKet.HanMucTK = 1000000000;
            return taiKhoanLienKet;
        }
    }
}