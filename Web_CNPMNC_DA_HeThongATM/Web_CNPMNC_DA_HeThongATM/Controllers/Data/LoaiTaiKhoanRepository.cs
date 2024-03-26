using FireSharp.Interfaces;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Controllers.Data
{
    public class LoaiTaiKhoanRepository : IRepository<LoaiTaiKhoan>
    {
        private readonly IFirebaseClient _client;

        public LoaiTaiKhoanRepository(IFirebaseClient client)
        {
            _client = client;
        }

        public IEnumerable<LoaiTaiKhoan> GetAll()
        {
            var response = _client.Get("LoaiTaiKhoan");
            if (response != null && response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var data = response.ResultAs<Dictionary<string, LoaiTaiKhoan>>();
                return data.Values;
            }
            else
            {
                // Trả về null hoặc thực hiện xử lý lỗi tùy thuộc vào yêu cầu của ứng dụng
                return null;
            }
        }

        public LoaiTaiKhoan GetById(string key)
        {
            var response = _client.Get($"LoaiTaiKhoan/{key}");
            if (response != null && response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return response.ResultAs<LoaiTaiKhoan>();
            }
            else
            {
                // Trả về null hoặc thực hiện xử lý lỗi tùy thuộc vào yêu cầu của ứng dụng
                return null;
            }
        }

        public void Insert(LoaiTaiKhoan entity)
        {

        }

        public void Update(LoaiTaiKhoan entity)
        {

        }

        public void Delete(string key)
        {

        }
    }

}
