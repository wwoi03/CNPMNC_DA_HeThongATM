namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class AnhCCCD
    {
        public string CCCD { get; set; }
        public string MatTruoc { get; set; }
        public string MatSau {  get; set; }
    }
}
//•	CCCD(string): lưu mã số CCCD (dây liên kết tới CCCD bảng KhachHang) 
//•	MatTruoc(string): lưu tên ảnh bên storage
//•	MatSau (string): lưu tên ảnh bên storage
