using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class FirebaseHelper
    {
        public static IFirebaseClient client;

        private IFirebaseConfig config = new FirebaseConfig
        {
            AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
            BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
        };
        public FirebaseHelper()
        {
            client = new FirebaseClient(config);
        }
        public static async Task WriteLog(string name, string Ds)
        {
            LogSystem logSystem = new LogSystem
            {
                Name = name,
                Description = Ds
            };
            FirebaseResponse response = await client.PushAsync("LogDebug", logSystem);
        }
        public async Task<List<CustommerViewModel>> GetCustommers()
        {
            try
            {
                FirebaseResponse response = await client.GetAsync("KhachHang");
                if (response != null)
                {
                    Dictionary<string, CustommerViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, CustommerViewModel>>(response.Body);
                    List<CustommerViewModel> peopleList = new List<CustommerViewModel>(data.Values);
                    return peopleList;
                }
                else
                {
                    Console.WriteLine(response.StatusCode);
                    return null;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return null;
            }
        }
        public async Task InsertCustommer(CustommerViewModel custommer)
        {
            try
            {
                FirebaseResponse response = await client.PushAsync("KhachHang", custommer);
                if (response != null)
                {

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);

            }
        }
        public async Task<string> CreateidCus()
        {
           

                FirebaseResponse response = await client.GetAsync("KhachHang");

                if (response == null || response.Body == "null")
                {
                    return ""; // Node không tồn tại hoặc trống
                   await  WriteLog("CreateidCus", "data null");
                }

                Dictionary<string, CustommerViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, CustommerViewModel>>(response.Body);
                List<CustommerViewModel> peopleList = new List<CustommerViewModel>(data.Values);

                return "CTM194"+(peopleList.Count + 501).ToString(); 


        }

        public async Task<bool> CheckCCCDExist(string cccdToCheck)
        {
            try
            {

                FirebaseResponse response = await client.GetAsync("KhachHang");

                if (response == null || response.Body == "null")
                {
                    return false; // Node không tồn tại hoặc trống
                }

                Dictionary<string, CustommerViewModel> data = response.ResultAs<Dictionary<string, CustommerViewModel>>();

                if (data != null && data.Values.Any(item => item.CCCD == cccdToCheck))//Any() để kiểm tra xem có bất kỳ phần tử nào trong danh sách có trường CCCD bằng với giá trị cccdToCheck hay không.
                {
                    return true; // CCCD tồn tại trong danh sách
                }

                return false; // CCCD không tồn tại trong danh sách
            }
            catch (Exception ex)
            {
                // Xử lý lỗi nếu có
                Console.WriteLine(ex.Message);
                return false;
            }
        }


        public async Task<CustommerViewModel> Get(string values)
        {
           
                FirebaseResponse response = await client.GetAsync("KhachHang");
                if (response != null)
                {
                    Dictionary<string, CustommerViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, CustommerViewModel>>(response.Body);
                   
                        var a = data.Values.FirstOrDefault(c => c.Makh == values);
                    if (a != null)
                    {
                        return a;
                    }
                    
                }
        }


        // Lấy danh sách khách hàng
        public List<KhachHang> GetCustomers()
        {
            List<KhachHang> dsKhachHang = new List<KhachHang>();
            FirebaseResponse response = client.Get("KhachHang");
            Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
            dsKhachHang = new List<KhachHang>(data.Values);
            return dsKhachHang;
        }

        // Tính tổng tài sản ngân hàng
        public double GetTotalAssets()
        {
            double totalAssets = 0;
            
            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            Dictionary<string, TaiKhoanLienKet> data = response.ResultAs<Dictionary<string, TaiKhoanLienKet>>();
            
            totalAssets = data.Values.Sum(item => item.SoDu);
           
            return totalAssets;
        }

        // Tính tổng giao dịch
        public long GetTotalTransaction()
        {
            long totalTransaction = 0;
            
            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            Dictionary<string, TaiKhoanLienKet> data = response.ResultAs<Dictionary<string, TaiKhoanLienKet>>();
           
            totalTransaction = data.Values.Count;

            return totalTransaction;
        }

            return null;
                
            }
        // Lấy danh sách nhân viên
        public List<NhanVien> GetStaffs()
		{
            FirebaseResponse response = client.Get("NhanVien");
            Dictionary<string, NhanVien> data = response.ResultAs<Dictionary<string, NhanVien>>();

            List<NhanVien> staffs = data.Values.ToList();

        }
            return staffs;
        }
    }
}


