using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp;
using FireSharp.Response;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class FirebaseHelperV2
    {
        //-----------------------------------------------------------------------SetupFireBase-----------------------------------------------------------
        private static readonly object _lock = new object();
        private static FirebaseHelperV2 _instance;
        public static IFirebaseClient Client { get; private set; }

        private IFirebaseConfig config = new FirebaseConfig
        {
            AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
            BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
        };

        private FirebaseHelperV2()
        {
            Client = new FirebaseClient(config);
        }

        public static FirebaseHelperV2 GetInstance()
        {
            lock (_lock)
            {
                if (_instance == null)
                {
                    _instance = new FirebaseHelperV2();
                }
                return _instance;
            }
        }
        public void InsertAppointment(DatLichHenViewModel datLichHen)
        {
            //FirebaseResponse response = client.Push("NhanVien", nhanVien);

            PushResponse response = Client.Push("DatLichHen", datLichHen);
            string newKey = response.Result.name;

            // Gán key vào trường Key của đối tượng NhanVien
            datLichHen.Key = newKey;

            // Cập nhật dữ liệu Nhân viên với key trong Firebase
            SetResponse setResponse = Client.Set("DatLichHen/" + newKey, datLichHen);
        }

    }
}
