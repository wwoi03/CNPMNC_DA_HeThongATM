namespace Web_CNPMNC_DA_HeThongATM.Models.ViewModel
{
    public class TrangThaiDatLichHenViewModel
    {
        public int StatusID { get; set; }
        public string StatusName { get; set; }
        public static List<TrangThaiDatLichHenViewModel> DefaultStatus()
        {
            List<TrangThaiDatLichHenViewModel> Default = new List<TrangThaiDatLichHenViewModel>()
            {
                 new TrangThaiDatLichHenViewModel { StatusID = 0, StatusName = "Chưa xác nhận" },
                 new TrangThaiDatLichHenViewModel { StatusID = 1, StatusName = "Đã xác nhận" },
                 new TrangThaiDatLichHenViewModel { StatusID = 2, StatusName = "Đã Hủy" },
            };
            return Default;
        } 
    }
}
