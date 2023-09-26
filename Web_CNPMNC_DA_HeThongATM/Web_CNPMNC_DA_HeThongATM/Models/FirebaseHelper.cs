using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;

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
        public async Task<List<CustommerViewModel>> InsertCustommer(CustommerViewModel custommer)
        {
            try
            {
                FirebaseResponse response = await client.PushAsync("KhachHang", custommer);
                if (response != null)
                {
                    Dictionary<string, CustommerViewModel> data = response.ResultAs<Dictionary<string, CustommerViewModel>>();
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
        public void ins(CustommerViewModel custommer)
        {
            FirebaseResponse response = client.PushAsync("KhachHang", custommer).Result;
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



    }
}

