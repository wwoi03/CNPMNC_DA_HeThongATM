using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System.Globalization;
using System.Security.Cryptography;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;

namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class FirebaseHelper
    {
        //-----------------------------------------------------------------------SetupFireBase-----------------------------------------------------------
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
        //-------------------------------------------------------------------------KHÁCH HÀNG--------------------------------------------------------------------------------------
        //lấy danh sách khách hàng
        public async Task<List<KhachHangViewModel>> GetCustommers()
        {
            try
            {
                FirebaseResponse response = await client.GetAsync("KhachHang");
                if (response != null && response.Body != "")
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
        public void InsertCustommer(KhachHangViewModel custommer)
        {
            try
            {
                string macuabomay = "autothention";
                FirebaseResponse response = client.Set("KhachHang/" + custommer.CCCD, custommer);
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

            //// Chuyển Guid thành byte array
            //byte[] bytes = uniqueGuid.ToByteArray();

            //// Chuyển byte array thành một chuỗi hexa
            //string hexString = BitConverter.ToString(bytes).Replace("-", "");

            //// Lấy 10 ký tự đầu tiên từ chuỗi hexa
            //string shortId = hexString.Substring(0, 10);
            string shortId = BitConverter.ToString(uniqueGuid.ToByteArray()).Replace("-", "").Substring(0, 10);

            return shortId;
        }


        //tạo pass ngẫu nhiên
        public string PassRandom()
        {
            string Makh = GuidSystem();


            FirebaseResponse response = client.Get("KhachHang");

            if (response != null && response.Body != "null")
            {
                Dictionary<string, KhachHangViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHangViewModel>>(response.Body);
                if (data != null && data.ContainsKey(Makh.ToString()))
                {
                    // Nếu ID đã tồn tại, thử tạo lại một ID mới
                    return PassRandom();
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
        public KhachHang GetCustomerbyid(string values)
        {

            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.CCCD == values);
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
            
            FirebaseResponse response = client.Get("LichSuGiaoDich");
            Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string, LichSuGiaoDich>>();
           
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

        // Lấy số lượng khách hàng theo năm hiện tại và theo từng tháng
        public Dictionary<string, int> GetQuantityCustomerByMonth(int year)
        {
            Dictionary<string, int> quantityCustomerByMonth = new Dictionary<string, int>();

            FirebaseResponse response = client.Get("KhachHang");
            Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
            
            // tính số lượng khách hàng theo từng tháng trong năm "year"
            for (int i = 1; i <= 12; i++)
            {
                int count = data.Values.Count(item => int.Parse(item.NgayTao.Split("/")[1]) == i && int.Parse(item.NgayTao.Split("/")[2]) == year);
                quantityCustomerByMonth.Add("Tháng " + i.ToString(), count);
            }

            return quantityCustomerByMonth;
        }

        // Lấy số lượng thẻ VISA và ATM
        
        // Tạo tài khoản nhân viên
        public void CreateStaff(NhanVien nhanVien)
        {
            FirebaseResponse response = client.Push("NhanVien", nhanVien);
        }

        //--------------------------------------------------------------------------THẺ NGÂN HÀNG ----------------------------------------------------------------------------------
        //tạo số thẻ ngân hàng
        private string GenerateCIF()
        {
            DateTime now = DateTime.Now;
            Random random = new Random();
            int r = random.Next(0, 99);
            // Tạo số CIF sử dụng thời gian và GUID ngẫu nhiên
            string cif = $"{now:HHmmss}{r.ToString("D2")}{r.ToString("D2")}";

            return "909090" + cif;
        }

        //tự động tạo mã số thẻ
        private string AccountNumber()
        {
            Random random = new Random();
            string digits = "";

            for (int i = 0; i < 10; i++)
            {
                int randomNumber = random.Next(0, 10); // Số ngẫu nhiên từ 0 đến 9
                digits += randomNumber.ToString();
            }

            return digits;
        }


        //tạo mã PIN
        public string CreatePIN()
        {
            string MaPIN = GenerateCIF();
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null && response.Body != "null")
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                if (data.ContainsKey(MaPIN))
                {
                    return CreatePIN();
                }

            }
            return MaPIN;
        }


        //tạo mã số thẻ trên database
        public string CreateAccountNumbet()
        {
            string MaSoThe = AccountNumber();
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null && response.Body != "null")
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                if (data.ContainsKey(MaSoThe))
                {
                    return CreateAccountNumbet();
                }
            }
            return MaSoThe;
        }


        //lấy key bằng cccd
        public string GetKeysBycccd(string values)
        {
            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);
                var keys = data.Where(entry => entry.Value.CCCD == values).Select(entry => entry.Key).ToList();
                if (keys != null)
                {
                    return string.Join(",", keys);
                }
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return "null";
        }


        //tạo thẻ ngân hàng
        public void CreateCard(TheNganHangViewModel card, string keys)
        {
            if (keys == null)
            {
                return;
            }
            else
            {
                FirebaseResponse firebaseResponse = client.Set("TheNganHang/" + $"{GetKeysBycccd(keys)}/", card);
                if (firebaseResponse != null)
                {
                    Console.WriteLine("thanh công");
                }
                else
                {
                    Console.WriteLine(firebaseResponse.StatusCode);
                }
            }
        }


        //tạo thẻ liên kết
        public void CreateCardLink(TaiKhoanLienKet taiKhoanLienKet)
        {
            FirebaseResponse response = client.Push("TaiKhoanLienKet", taiKhoanLienKet);
            if (response != null)
            {
                Console.WriteLine("thành công");

            }
            else { Console.WriteLine("thất bại"); }
        }



        //đừng xóa em đang sửa
        //public string GetAccount(string Value)
        //{
        //    FirebaseResponse response = client.Get("TaiKhoanLienKet");
        //    if (response != null)
        //    {
        //        Dictionary<string, AccountViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, AccountViewModel>>(response.Body);

        //        // Sử dụng LINQ để lấy keys của các bản ghi có SoTaiKhoan trùng với Value
        //        var matchingKeys = data.Where(entry => entry.Value.SoTaiKhoan == Value).Select(entry => entry.Key).ToList();

        //        if (matchingKeys.Count > 0)
        //        {
        //            // matchingKeys là một danh sách các keys có SoTaiKhoan trùng với Value
        //            // Ở đây, bạn có thể làm gì đó với danh sách này
        //            return string.Join(", ", matchingKeys);
        //        }
        //    }
        //    else
        //    {
        //        Console.WriteLine(response.StatusCode);
        //    }
        //    return null;
        //}

        //----------------------------------------------------------LOAI THE NGAN HANG------------------------------------------------
        // Lấy danh sách loại thẻ
        public List<LoaiTheNganHang> GetCardTypes()
        {
            List<LoaiTheNganHang> cardtypes;
            FirebaseResponse response = client.Get("LoaiTheNganHang");
            Dictionary<string, LoaiTheNganHang> data = response.ResultAs<Dictionary<string, LoaiTheNganHang>>();
            cardtypes = new List<LoaiTheNganHang>(data.Values);
            return cardtypes;
        }
        // Lấy key loại thẻ bằng tên 
        public string getCardTypeKeybyName(string loaithe)
        {
            FirebaseResponse response = client.Get("LoaiTheNganHang");
            if (response != null)
            {
                Dictionary<string, LoaiTheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, LoaiTheNganHang>>(response.Body);
                var keys = data.Where(entry => entry.Value.TenTNH == loaithe).Select(entry => entry.Key).ToList();
                if (keys != null)
                {
                    return string.Join(",", keys);
                }
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return "null";
        }
        //Lấy dữ liệu thẻ ngân hàng dựa trên customerKey và key loai the
        public TheNganHang getCardbyCusTypeKeys(string cusKey, string typeKey)
        {

            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaKH == cusKey && c.LoaiThe == typeKey);
                if (a != null)
                {
                    return a;
                }

            }
            return null;
        }
        //Đổi trạng thái khóa thẻ
        public void ChangeCardStatus(long masothe)
        {
            Dictionary<string, object> updates;
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                if(getCardbyID(masothe).TinhTrangThe==1 || getCardbyID(masothe).TinhTrangThe == 2)
                {
                     updates = new Dictionary<string, object>
                    {
                        {"TinhTrangThe" , 0},
                    };
                }
               else
                {
                    updates = new Dictionary<string, object>
                    {
                        {"TinhTrangThe" , 2},
                    };
                }
                string path = $"TheNganHang/{getKeyCardbyMaSo(masothe)}";
                client.Update(path, updates);
            }
        }
        //Lấy thẻ bằng mã số 
        public TheNganHang getCardbyID(long maso)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaSoThe == maso );
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //Lấy key thẻ bằng mã số
        public string getKeyCardbyMaSo(long maso)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                var keys = data.Where(entry => entry.Value.MaSoThe == maso).Select(entry => entry.Key).ToList();
                if (keys != null)
                {
                    return string.Join(",", keys);
                }
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return "null";
        }
        //----------------------------------------------------------LICH SU GIAO DICH------------------------------------------------
        //Lấy dữ liệu thẻ ngân hàng dựa trên customerKey
        public TheNganHang getCardbyCusKeys(string cusKey)
        {

            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaKH == cusKey);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //Lay  tk bang ma so the
        public TaiKhoanLienKet getAccountbyCardKey(long cardKey)
        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaSoThe == cardKey);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //TKLK == SoTK
        public TaiKhoanLienKet getAccountbyKey(long stk)
        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.SoTaiKhoan == stk);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //Lay LichSuGD tren STK 
        public LichSuGiaoDich getHistorybySTK(long stk)
        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, LichSuGiaoDich> data = JsonConvert.DeserializeObject<Dictionary<string, LichSuGiaoDich>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.SoTaiKhoan == stk);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //DSLSGD tang
        public List<LichSuGiaoDich> getListHistoryUPbySTK(long stk)
        {
            List<LichSuGiaoDich> history;
            FirebaseResponse response = client.Get("LichSuGiaoDich");
            Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string,LichSuGiaoDich>>();
            history = data.Values.Where(item =>  item.TaiKhoanNhan==stk).ToList();
            return history;
        }
        //DSLSGD giam
        public List<LichSuGiaoDich> getListHistoryDownbySTK(long stk)
        {
            List<LichSuGiaoDich> history;
            FirebaseResponse response = client.Get("LichSuGiaoDich");
            Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string, LichSuGiaoDich>>();
            history = data.Values.Where(item => item.SoTaiKhoan == stk).ToList();
            return history;
        }
        //DSLSGD drftghyhgfdsfdghgfdesdfghnmhjgfrghjmhgytrfghyjhytghj
        public List<LichSuGiaoDich> getListHistorybySTK(long stk)
        {
            List<LichSuGiaoDich> history;
            FirebaseResponse response = client.Get("LichSuGiaoDich");
            Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string, LichSuGiaoDich>>();
            history = data.Values.Where(item => item.SoTaiKhoan == stk || item.TaiKhoanNhan == stk).ToList();
            return history;
        }
        //DSGIUIKGJHJBVGBJBKLIHGBVJKBfdtttr***************************************************************
        public List<LichSuGiaoDich> getListHistorybySTK()
        {
            List<LichSuGiaoDich> history;
            FirebaseResponse response = client.Get("LichSuGiaoDich");
            Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string, LichSuGiaoDich>>();
            history = new List<LichSuGiaoDich>(data.Values);
            return history;
        }
        public List<LichSuGiaoDichViewModel> danhsachLSGD()
        {
            List<LichSuGiaoDichViewModel> LSGD = new List<LichSuGiaoDichViewModel>();
            foreach (LichSuGiaoDich item in getListHistorybySTK())
            {
                string datetime = $"{item.NgayGiaoDich} {item.GioGiaoDich}";
                DateTime date = DateTime.ParseExact(datetime, "dd/MM/yyyy HH:mm:ss", CultureInfo.InvariantCulture);
                LichSuGiaoDichViewModel ls = new LichSuGiaoDichViewModel(item,"huhu",date, "bbb");
                LSGD.Add(ls);
                
            }
            // Sắp xếp danh sách theo trường DateTime
            LSGD = LSGD.OrderBy(item => item.date).ToList();
            return LSGD;
        }

        //Saoke tr oi met qua************************************************
        public List<LichSuGiaoDichViewModel> getLSGD(long stk, DateTime from, DateTime to)
        {
            List<LichSuGiaoDichViewModel> LSGD = new List<LichSuGiaoDichViewModel>();
           
            string tenKH= GetCustomerbyid(getCardbyID(getAccountbyKey(stk).MaSoThe).MaKH).TenKhachHang;
            foreach (LichSuGiaoDich item in getListHistorybySTK(stk))
            {
                string datetime = $"{item.NgayGiaoDich} {item.GioGiaoDich}";
                DateTime date = DateTime.ParseExact(datetime, "dd/MM/yyyy HH:mm:ss", CultureInfo.InvariantCulture);
                if (date >= from && date <= to)
                {
                    string status="";
                    if (stk == item.SoTaiKhoan)
                    {
                        status = "Giảm";
                    }
                    else if (stk == item.TaiKhoanNhan)
                        status = "Tăng";
                    
                    LichSuGiaoDichViewModel ls = new LichSuGiaoDichViewModel(item, status,date,tenKH);
                    LSGD.Add(ls);
                }
            }
            // Sắp xếp danh sách theo trường DateTime
            LSGD = LSGD.OrderBy(item => item.date).ToList();
            return LSGD;
        }
     
        ////Kiem tra lich su giao dich
        //public bool checkGiaoDich(long stk)
        //{
        //    LichSuGiaoDich gd = getHistorybySTK(stk);
        //    if(gd.SoTaiKhoan)
        //}
    }
}

