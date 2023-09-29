using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using UniqueIdGenerator;


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
       
        //lấy danh sách khách hàng
        public async Task<List<KhachHangViewModel>> GetCustommers()
        {
            try
            {
                FirebaseResponse response = await client.GetAsync("KhachHang");
                if (response != null)
                {
                    Dictionary<string, KhachHangViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHangViewModel>>(response.Body);
                    List<KhachHangViewModel> peopleList = new List<KhachHangViewModel>(data.Values);
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
        //tạo khách hàng
        public async Task InsertCustommer(KhachHangViewModel custommer)
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
        //tạo id khách hàng bằng GUID (Globally Unique Identifier)
        private string GuidSystem()
        {
            // Tạo một Guid mới
            Guid uniqueGuid = Guid.NewGuid();

            // Chuyển Guid thành byte array
            byte[] bytes = uniqueGuid.ToByteArray();

            // Chuyển byte array thành một chuỗi hexa
            string hexString = BitConverter.ToString(bytes).Replace("-", "");

            // Lấy 10 ký tự đầu tiên từ chuỗi hexa
            string shortId = hexString.Substring(0, 10);

            return shortId;
        }
        public async Task<string> CreateidCus()
        {
            string Makh = GuidSystem();


            FirebaseResponse response = await client.GetAsync("KhachHang");

            if (response == null || response.Body == "null")
            {
                Dictionary<string, KhachHangViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHangViewModel>>(response.Body);
                if (data.ContainsKey(Makh.ToString()))
                {
                    // Nếu ID đã tồn tại, thử tạo lại một ID mới
                    return await CreateidCus();
                }

            }

            return Makh.ToString();


        }
        //check cccd
        public async Task<bool> CheckCCCDExist(string cccdToCheck)
        {
            try
            {

                FirebaseResponse response = await client.GetAsync("KhachHang");

                if (response == null || response.Body == "null")
                {
                    return false; // Node không tồn tại hoặc trống
                }

                Dictionary<string, KhachHangViewModel> data = response.ResultAs<Dictionary<string, KhachHangViewModel>>();

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

        //lây dữ liệu khách hàng theo id
        public async Task<KhachHangViewModel> GetCustomerbyid(string values)
        {

            FirebaseResponse response = await client.GetAsync("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHangViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHangViewModel>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.Makh == values);
                if (a != null)
                {
                    return a;
                }

            }
            return null;
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




        // Lấy danh sách nhân viên
        public List<NhanVien> GetStaffs()
        {
            FirebaseResponse response = client.Get("NhanVien");
            Dictionary<string, NhanVien> data = response.ResultAs<Dictionary<string, NhanVien>>();

            List<NhanVien> staffs = data.Values.ToList();


            return staffs;
        }
    }

}

