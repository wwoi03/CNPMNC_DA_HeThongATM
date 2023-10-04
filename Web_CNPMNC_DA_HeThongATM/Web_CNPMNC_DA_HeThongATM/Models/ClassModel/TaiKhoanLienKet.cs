namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class TaiKhoanLienKet
    {
        public double HanMucThe { get; set; }
        public long MaSoThe { get; set; }
        public string NgayGD { get; set; }
        public double SoDu { get; set; }
        public long SoTaiKhoan { get; set; }
        public string TenTaiKhoan { get; set; }
        public double TienDaGD { get; set; }
        public double TienGD1Lan { get; set; }
        public int TinhTrangTaiKhoan { get; set; }
        public string LoaiTaiKhoan { get; set; }

        /*public static TaiKhoanLienKet DefaultCard(string Stk, string TenTaiKhoan, string MaSoThe)
        {
            TaiKhoanLienKet taiKhoanLienKet = new TaiKhoanLienKet();
            taiKhoanLienKet.SoDu = 50000;
            taiKhoanLienKet.NgayGD = "abc";
            taiKhoanLienKet.SoTaiKhoan = Stk;
            taiKhoanLienKet.TienDaGD = 0;
            taiKhoanLienKet.TienGD1Lan = 0;
            taiKhoanLienKet.TinhTrangTaiKhoan = 1;
            taiKhoanLienKet.TenTaiKhoan = TenTaiKhoan;
            taiKhoanLienKet.MaSoThe = MaSoThe;
            taiKhoanLienKet.HanMucThe = 1000000000;
            return taiKhoanLienKet;
        }*/

    }


}
