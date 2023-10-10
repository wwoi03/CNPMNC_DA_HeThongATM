namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class LaiSuat
    {
        public string MaLaiSuat {  get; set; }
        public string KyHan {  get; set; }
        public double TiLe { get; set; }
    }
}
//•	MaLaiSuat(string): bằng với key của record
//•	KyHan (string): kỳ hạn lãi suất VD 1 tháng – 2 tháng – 3 tháng
//•	TiLe (double): tỉ lệ của kỳ hạn
