using Microsoft.EntityFrameworkCore.Metadata.Internal;
using System.Net.NetworkInformation;

namespace Web_CNPMNC_DA_HeThongATM.Models.ClassModel
{
    public class LoaiTheNganHang
    {
        public string HanMucLoaiThe {  get; set; }
        public string MaLoaiTNH {  get; set; }
        public string TenTNH { get; set; }
    }
}
//============================================================================================NOTE==================================================================================================================
//•	HanMucLoaiThe(double): hạn mức loại thẻ
//•	MaLoaiTNH (string): giống với(key của record)
//•	TenTNH(string): tên thẻ
