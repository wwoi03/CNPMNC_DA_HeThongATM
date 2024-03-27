using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;

using Web_CNPMNC_DA_HeThongATM.Models;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Designpattern.Singleton
{
	public class FirebaseSingleton
	{
		private static readonly object _lock = new object();
		private static FirebaseSingleton _instance;
		public static IFirebaseClient Client { get; private set; }
		private IFirebaseConfig config = new FirebaseConfig
		{
			AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
			BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
		};
		

		private FirebaseSingleton()
		{
			Client = new FirebaseClient(config);
		}

		public static FirebaseSingleton GetInstance()
		{
			lock (_lock)
			{
				if (_instance == null)
				{
					_instance = new FirebaseSingleton();
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



		public List<DatLichHen> GetAppointent()
		{
			List<DatLichHen> dslichhen = new List<DatLichHen>();
			FirebaseResponse response = Client.Get("DatLichHen");
			Dictionary<string, DatLichHen> data = response.ResultAs<Dictionary<string, DatLichHen>>();
			dslichhen = new List<DatLichHen>(data.Values);
			return dslichhen;
	}
		public DatLichHen GetAppointmentbyKey(string values)
		{

			FirebaseResponse response = Client.Get("DatLichHen");
			if (response != null)
			{
				Dictionary<string, DatLichHen> data = JsonConvert.DeserializeObject<Dictionary<string, DatLichHen>>(response.Body);

				var a = data.Values.FirstOrDefault(c => c.Key == values);
				if (a != null)
				{
					return a;
				}

			}
			return null;
		}
		public DatLichHen GetLichHenByKey(string key)
		{
			try
			{
				FirebaseResponse response = Client.Get("DatLichHen/" + key);
				if (response == null || string.IsNullOrEmpty(response.Body))
				{
					return null;
				}

				//DatLichHenViewModel datLichHen = JsonConvert.DeserializeObject<DatLichHenViewModel>(response.Body);
				Dictionary<string, DatLichHen> data = JsonConvert.DeserializeObject<Dictionary<string, DatLichHen>>(response.Body);
				return data.Values.FirstOrDefault(p => p.Key == key);
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.Message);
				return null;
			}
		}
		public bool TrangThai(int trangThai, string key)
		{
			try
			{
				FirebaseResponse response = Client.Set("DatLichHen/" + key + "/TrangThai", trangThai);
				if (response != null)
				{
					return true;
				}
				else
				{
					return false;
				}

			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.Message);
				return false; // Cập nhật thất bại
			}
		}
	}
}