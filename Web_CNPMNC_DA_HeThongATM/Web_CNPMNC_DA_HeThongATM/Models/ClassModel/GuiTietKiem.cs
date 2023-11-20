using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class GuiTietKiem
    {
        public string Key { get; set; }
        public long TaiKhoanTietKiem { get; set; }
        public long TaiKhoanNguon { get; set; }
        public string LaiSuatKey { get; set; }
        public string NgayGui { get; set; }
        public double TienGui { get; set; }

        public double TienLaiToiKy { get; set; }

        public string MaKHKey { get; set; }
    }
}