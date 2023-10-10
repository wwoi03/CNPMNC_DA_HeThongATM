using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class GuiTietKiem
    {
        public long SoTaiKhoan { get; set; }
        public long TaiKhoanNguon { get; set; }
        public string NgayGui { get; set; }
        public long KyHan { get; set; }
    }
}
//•	KyHan(string): key của record bên bảng LaiSuat
//•	NgayGui (string): ngày gửi tiết kiệm
//•	SoTaiKhoan (long): số tài khoản tiết kiệm
//•	SoTaiKhoanNguon (long): số tài khoản nguồn
