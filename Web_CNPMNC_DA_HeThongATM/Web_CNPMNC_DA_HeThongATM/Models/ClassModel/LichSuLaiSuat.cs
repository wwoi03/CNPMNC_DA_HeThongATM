using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class LichSuLaiSuat
    {
        public string MaGuiTietKiem { get; set; }
        public long SoTienLai { get; set; }
        public string NgayNhan { get; set; }
        public long SoDuTaiKhoanNguon { get; set; }
    }
}
//•	MaGuiTietKiem(string): key của record bên bảng GuiTietKiem
//•	NgayNhan (string): theo ngày/tháng/năm
//•	SoDuTaiKhoanNguon (double): số dư tài khoản nguồn lúc đó khi được cộng tiền lãi
//•	SoTienLai (double): số tiền lại ở kỳ hạn đó
