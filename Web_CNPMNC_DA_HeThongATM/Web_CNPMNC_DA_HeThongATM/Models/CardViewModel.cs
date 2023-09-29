using System.ComponentModel.DataAnnotations;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class CardViewModel
    {
        [Required]
        public string MaKH { get; set; }
        public string MaPIN { get; set; }
        public string MaSoThe { get; set; }
        public string NgayMoThe { get; set; }
        public string NgayHetHan { get; set; }
        public string SDTDangKy { get; set; }   
        public string TinhTrangThe { get; set; }
        public string Loaithe { get; set; }
    }
}
