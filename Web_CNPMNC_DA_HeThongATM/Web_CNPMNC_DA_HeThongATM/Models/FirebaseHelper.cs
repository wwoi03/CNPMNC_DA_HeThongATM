using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class FirebaseHelper
    {
        public FirebaseHelper(){}
       private IFirebaseConfig config = new FirebaseConfig
        {
            AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
            BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
        };
        
        public  async Task<List<CustommerViewModel>> GetCustommers()
        {
            try
            {
                IFirebaseClient client = new FirebaseClient(config);
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
        public  async Task<List<CustommerViewModel>> InsertCustommer(CustommerViewModel custommer)
        {
            try {
                IFirebaseClient client = new FirebaseClient(config);
                FirebaseResponse response = await client.PushAsync("KhachHang", custommer);
                if(response != null)
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
    }
    

}
