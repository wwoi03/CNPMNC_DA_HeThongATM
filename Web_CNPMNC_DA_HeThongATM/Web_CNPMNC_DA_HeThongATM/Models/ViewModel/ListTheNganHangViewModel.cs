using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class ListTheNganHangViewModel
    {

        public string MaKH { get; set; }
        public string TenKhachHang { get; set; }

        public string MaPin { get; set; }


        public long MaSoThe { get; set; }


        public string NgayMoThe { get; set; }


        public string NgayHetHan { get; set; }

        public long SoTaiKhoan { get; set; }

        public int TinhTrangThe { get; set; }

      
    }
}
