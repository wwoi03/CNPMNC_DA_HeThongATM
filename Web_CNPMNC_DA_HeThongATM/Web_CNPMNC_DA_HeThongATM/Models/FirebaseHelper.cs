using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using UniqueIdGenerator;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using System.Globalization;

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
        // Lấy danh sách khách hàng
        public List<KhachHang> GetCustomers()
        {
            List<KhachHang> dsKhachHang = new List<KhachHang>();
            FirebaseResponse response = client.Get("KhachHang");
            Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
            dsKhachHang = new List<KhachHang>(data.Values);
            return dsKhachHang;
        }

        //tạo khách hàng
        public void InsertCustommer(KhachHangViewModel custommer)
        {
            try
            {
                string macuabomay = "autothention";
                FirebaseResponse response = client.Push("KhachHang", custommer);
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


        //lấy tên  khách hàng qua id
        public string GetNameCustomerbyid(string values)
        {
            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);
                if (data.TryGetValue(values, out KhachHang khachHang))
                {
                    return khachHang.TenKH;
                }
                //var customer = data.Values.FirstOrDefault(kh => kh.CCCD == values);

                //if (customer != null)
                //{
                //    return customer.TenKhachHang;
                //}
            }
            return "";
        }




        //Kiểm tra Sdt nhập vào form
        public bool CheckSdt(string values)
        {

            FirebaseResponse response = client.Get("KhachHang");
            if (response != null && response.Body != null)
            {
                Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
                if (data.Values.Any(c => c.SoDienThoai == values))
                    return true;
                return false;

            }
            return false;
        }
        public KhachHang SearchCustomer(string searchCustomer)
        {
            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);

                var card = data.Values.FirstOrDefault(c => c.CCCD == searchCustomer);

                return card;

                //// Nếu không tìm thấy theo CCCD, thử tìm theo ID thẻ)
                //if (card == null)
                //{
                //    card = data.Values.FirstOrDefault(c => c.MaSoThe == long.Parse(searchValue) || c.SDTDangKy == searchValue);
                //}

                //// Nếu tìm thấy thẻ, trả về thông tin của họ
                //if (card != null)
                //{
                //    return card;
                //}
            }

            // Trả về null nếu không tìm thấy khách hàng
            return null;
        }

        //-------------------------------------------------------------------------------Nhân Viên---------------------------------------------------

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

            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();

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
            Random random = new Random();
            string digits = "";

            for (int i = 0; i < 6; i++)
            {
                int randomNumber = random.Next(0, 10); // Số ngẫu nhiên từ 0 đến 9
                digits += randomNumber.ToString();
            }

            return digits;

        }

        //tự động tạo mã số thẻ
        private string AccountNumber()
        {
            DateTime now = DateTime.Now;
            Random random = new Random();
            int r = random.Next(0, 99);
            // Tạo số CIF sử dụng thời gian và GUID ngẫu nhiên
            string cif = $"{now:HHmmss}{r.ToString("D2")}{r.ToString("D2")}";

            return "909090" + cif;
        }


        //tạo mã PIN
        public string CreatePin()
        {
            string MaSoThe = GenerateCIF();
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


        //tạo mã số thẻ trên database
        public string CreateAccountNumbet()
        {
            string stk = AccountNumber();
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null && response.Body != "null")
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                if (data.ContainsKey(stk))
                {
                    return CreatePin();
                }

            }
            return stk;

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
            card.MaKH = keys;
            // FirebaseResponse firebaseResponse = client.Set("TheNganHang/" + $"{GetKeysBycccd(keys)}/", card);
            FirebaseResponse firebaseResponse = client.Push("TheNganHang", card);
            if (firebaseResponse != null)
            {
                Console.WriteLine("thanh công");
            }
            else
            {
                Console.WriteLine(firebaseResponse.StatusCode);
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

        //danh sách thẻ 
        public List<TheNganHang> getListCard()
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                List<TheNganHang> theNganHangs = new List<TheNganHang>(data.Values);
                return theNganHangs;
            }
            return null;
        }







        //lấy thẻ theo id
        public TheNganHang GetTheNganHangById(long masothe)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                TheNganHang thenganhang = data.Values.FirstOrDefault(p => p.MaSoThe == masothe);
                return thenganhang;
            }
            return null;
        }


        //------------------------------------------------------------------Chuyển Tiền-----------------------------------------------------
        //Chuyển tiền


        public void SendMoney(string numberSend)
        {
            // lấy thông tin khách hàng chuyển
            FirebaseResponse responseSend = client.Get("KhachHang" + numberSend);
            if (responseSend != null)
            {

            }
            //lấy thông tin khách hàng gửi
            FirebaseResponse responseGet = client.Get("KhachHang");
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
        public List<LoaiTaiKhoan> GetTypes()
        {
            List<LoaiTaiKhoan> cardtypes;
            FirebaseResponse response = client.Get("LoaiTaiKhoan");
            Dictionary<string, LoaiTaiKhoan> data = response.ResultAs<Dictionary<string, LoaiTaiKhoan>>();
            cardtypes = new List<LoaiTaiKhoan>(data.Values);
            return cardtypes;
        }
        // Lấy key loại thẻ bằng tên 
        public string getTypeKeybyName(string loaithe)
        {
            FirebaseResponse response = client.Get("LoaiTaiKhoan");
            if (response != null)
            {
                Dictionary<string, LoaiTaiKhoan> data = JsonConvert.DeserializeObject<Dictionary<string, LoaiTaiKhoan>>(response.Body);
                var keys = data.Where(entry => entry.Value.TenLoaiTaiKhoan == loaithe).Select(entry => entry.Key).ToList();
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

        //Đổi trạng thái khóa thẻ
        public void ChangeCardStatus(long masothe)
        {
            Dictionary<string, object> updates;
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                if (getCardbyID(masothe).TinhTrangThe == 1 || getCardbyID(masothe).TinhTrangThe == 2)
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
        //Lay the = stk
        public TheNganHang getCardbyAccountID(long stk)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.SoTaiKhoan == stk);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //Lấy thẻ bằng mã số 
        public TheNganHang getCardbyID(long maso)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaSoThe == maso);
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
        //Lay tk = cccd
        public TaiKhoanLienKet getAccountbyCusKey(string key)
        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.MaKHKey == key);
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
        public GiaoDich getHistorybySTK(long stk)
        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, GiaoDich> data = JsonConvert.DeserializeObject<Dictionary<string, GiaoDich>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.TaiKhoanNguon == stk || c.TaiKhoanNhan == stk);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }
        //DSLSGD tang
        public List<GiaoDich> getListHistoryUPbySTK(long stk)
        {
            List<GiaoDich> history;
            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();
            history = data.Values.Where(item => item.TaiKhoanNhan == stk).ToList();
            return history;
        }
        //DSLSGD giam
        public List<GiaoDich> getListHistoryDownbySTK(long stk)
        {
            List<GiaoDich> history;
            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();
            history = data.Values.Where(item => item.TaiKhoanNguon == stk).ToList();
            return history;
        }
        //DSLSGD drftghyhgfdsfdghgfdesdfghnmhjgfrghjmhgytrfghyjhytghj
        public List<GiaoDich> getListHistorybySTK(long stk)
        {
            List<GiaoDich> history;
            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();
            history = data.Values.Where(item => item.TaiKhoanNguon == stk || item.TaiKhoanNhan == stk).ToList();
            return history;
        }
        //DSGIUIKGJHJBVGBJBKLIHGBVJKBfdtttr***************************************************************
        public List<GiaoDich> getListHistory()
        {
            List<GiaoDich> history;
            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();
            history = new List<GiaoDich>(data.Values);
            return history;
        }
        public List<ThongKeViewModel> danhsachLSGD()
        {
            List<ThongKeViewModel> LSGD = new List<ThongKeViewModel>();
            foreach (GiaoDich item in getListHistory())
            {
                string datetime = $"{item.NgayGiaoDich} {item.GioGiaoDich}";
                DateTime date = DateTime.ParseExact(datetime, "dd/MM/yyyy HH:mm:ss", CultureInfo.InvariantCulture);
                string tenKH = getCusbyKey(getAccountbyKey(item.TaiKhoanNguon).MaKHKey).TenKH;
                string tenKH2 = getCusbyKey(getAccountbyKey(item.TaiKhoanNhan).MaKHKey).TenKH;
               ThongKeViewModel ls = new ThongKeViewModel(item, "Chuyển khoản", date, tenKH, tenKH2);
                LSGD.Add(ls);

            }
            // Sắp xếp danh sách theo trường DateTime
            LSGD = LSGD.OrderByDescending(item => item.date).ToList();
            return LSGD;
        }

        //Saoke tr oi met qua************************************************
        public List<GiaoDichViewModel> getLSGD(long stk, DateTime from, DateTime to)
        {
            List<GiaoDichViewModel> LSGD = new List<GiaoDichViewModel>();
            DateTime fromDate = new DateTime(from.Year, from.Month, from.Day, 0, 0, 0);
            DateTime toDate = new DateTime(to.Year, to.Month, to.Day, 23, 59, 59);

            string tenKH = getCusbyKey(getAccountbyKey(stk).MaKHKey).TenKH;
            foreach (GiaoDich item in getListHistorybySTK(stk))
            {
                string datetime = $"{item.NgayGiaoDich} {item.GioGiaoDich}";
                DateTime date = DateTime.ParseExact(datetime, "dd/MM/yyyy HH:mm:ss", CultureInfo.InvariantCulture);
                double sodu = 0;
                if (date >= fromDate && date <= toDate)
                {
                    string status = "";
                    string tenKH2 = "";
                    if (stk == item.TaiKhoanNguon)
                    {
                        tenKH2 = getCusbyKey(getAccountbyKey(item.TaiKhoanNhan).MaKHKey).TenKH;
                        status = "Chuyển đi";
                        sodu = item.SoDuLucGui;
                    }
                    else if (stk == item.TaiKhoanNhan)
                    {
                        status = "Nhận về";
                        tenKH2 = getCusbyKey(getAccountbyKey(item.TaiKhoanNguon).MaKHKey).TenKH;
                        sodu = item.SoDuLucNhan;
                    }

                    GiaoDichViewModel ls = new GiaoDichViewModel(item, status, date, tenKH, tenKH2,sodu);
                    LSGD.Add(ls);
                }
            }
            // Sắp xếp danh sách theo trường DateTime
            LSGD = LSGD.OrderByDescending(item => item.date).ToList();
            return LSGD;
        }

        ////Kiem tra lich su giao dich
        //public bool checkGiaoDich(long stk)
        //{
        //    GiaoDich gd = getHistorybySTK(stk);
        //    if(gd.SoTaiKhoan)
        //}
        //Lay customer by key
        public KhachHang getCusbyKey(string key)
        {
            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);
                KhachHang khachHang = data[key];
                if (khachHang != null)
                    return khachHang;
            }
            return null;
        }


    }
}

