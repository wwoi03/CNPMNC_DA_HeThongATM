using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using UniqueIdGenerator;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using System.Net;

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
            Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);
            dsKhachHang = new List<KhachHang>(data.Values);
            return dsKhachHang;
        }

        //tạo khách hàng
        public void InsertCustommer(KhachHangViewModel custommer)
        {
            try
            {
              
                FirebaseResponse response =  client.Push("KhachHang", custommer);
                if (response != null)
                {
                    var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                    string newKey = data["name"];
                    FirebaseResponse updateResponse = client.Set("KhachHang/" + newKey + "/Key", newKey);
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
        public async Task<string> GetNameCustomerbyid(string values)
        {
            FirebaseResponse response = await client.GetAsync("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);        
                if(data.TryGetValue(values, out KhachHang khachHang))
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
        private string AccountNumber(string request)
        {
            DateTime now = DateTime.Now;
            Random random = new Random();
            int r = random.Next(0, 99);
            // Tạo số CIF sử dụng thời gian và GUID ngẫu nhiên
            string cif = $"{now:HHmmss}{r.ToString("D2")}{r.ToString("D2")}";
            if(request.Equals("masothe"))
                return "909090" + cif;
            return "072"+cif;

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
            string stk = AccountNumber("masothe");
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
        public string CreateSotaikhoan()
        {
            string stk = AccountNumber("sotaikhoan");
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null && response.Body != "null")
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                if (data.ContainsKey(stk))
                {
                    return CreateSotaikhoan();
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
        public void CreateCard(TheNganHang card)
        {
           
            // FirebaseResponse firebaseResponse = client.Set("TheNganHang/" + $"{GetKeysBycccd(keys)}/", card);
            FirebaseResponse firebaseResponse = client.Push("TheNganHang", card);
            if (firebaseResponse != null)
            {
                // Phân tích chuỗi JSON để lấy key
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(firebaseResponse.Body);
                string newKey = data["name"];

               

                // Cập nhật đối tượng TheNganHang với MaKHKey mới
                FirebaseResponse updateResponse = client.Set("TheNganHang/"+newKey+"/Key", newKey);

                if (updateResponse.StatusCode == HttpStatusCode.OK)
                {
                    Console.WriteLine("thanh công");
                }
                else
                {
                    Console.WriteLine(updateResponse.StatusCode);
                }

            }
        }
      

        //tạo thẻ liên kết
        public void CreateCardLink(TaiKhoanLienKet taiKhoanLienKet)
        {
            FirebaseResponse response = client.Push("TaiKhoanLienKet", taiKhoanLienKet);

            if (response != null)
            {
                // Phân tích chuỗi JSON để lấy key
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                string newKey = data["name"];



                // Cập nhật đối tượng TheNganHang với MaKHKey mới
                FirebaseResponse updateResponse = client.Set("TaiKhoanLienKet/" + newKey + "/Key", newKey);

                if (updateResponse.StatusCode == HttpStatusCode.OK)
                {
                    Console.WriteLine("thanh công");
                }
                else
                {
                    Console.WriteLine(updateResponse.StatusCode);
                }

            }


        }
        //danh sách thẻ 
        public async Task<List<TheNganHang>> getListCard()
        {
            FirebaseResponse response = await client.GetAsync("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                List<TheNganHang> theNganHangs = new List<TheNganHang>(data.Values);
                return theNganHangs;

            }
            return null;
        }


        //lấy tên loại thẻ ngân hàng
        //public string GetNameTypeCard(string values)
        //{
        //    FirebaseResponse response = client.Get("LoaiTheNganHang");
        //    if (response != null)
        //    {
        //        Dictionary<string, LoaiTheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, LoaiTheNganHang>>(response.Body);

        //        var nametypes = data.Values.FirstOrDefault(kh => kh.MaLoaiTNH == values);

        //        if (nametypes != null)
        //        {
        //            return nametypes.TenTNH;
        //        }

        //    }
        //    return "";
        //}

        //tìm kiếm thẻ theo cccd/ id thẻ/số điện thoại
        public TheNganHang SearchCard(string searchValue)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if (response != null)
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);

                // Thử tìm theo CCCD (Căn cước công dân)
                var card = data.Values.FirstOrDefault(c => c.Key == searchValue);



                // Nếu không tìm thấy theo CCCD, thử tìm theo ID thẻ)

                if (card == null)
                {
                    card = data.Values.FirstOrDefault(c => c.MaSoThe == long.Parse(searchValue) || c.SoTaiKhoan == long.Parse(searchValue));
                }

                // Nếu tìm thấy thẻ, trả về thông tin của họ
                if (card != null)
                {
                    return card;
                }
            }

            // Trả về null nếu không tìm thấy khách hàng
            return null;
        }


        //lấy thẻ theo id
        public TheNganHang GetTheNganHangById(long masothe)
        {
            FirebaseResponse response = client.Get("TheNganHang");
            if(response != null)
            {
                Dictionary<string,TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
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
        public string GetAccount(long Value)
        {
            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);
                var accountData = data.Where(entry => entry.Value.SoTaiKhoan == Value).Select(entry => entry.Key).ToList();
                if (accountData != null)
                {
                    return string.Join(",", accountData);
                }
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return null;

        }
        public bool ChuyenTien(double soTien, long value)
        {
            string info = GetAccount(value);
            try
            {
                FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);

                // Lấy dữ liệu tài khoản từ Firebase
                var accountData = response.ResultAs<TaiKhoanLienKetViewModel>();

                ////  Chuyển tiền xong cộng số tiền vào số dư
                //accountData.SoDu += soTien;
                Double SoDu = accountData.SoDu + soTien;


                // Cập nhật số dư trên Firebase
                client.Set("TaiKhoanLienKet/" + info + "/SoDu", SoDu);

                return true; // Cập nhật thành công
            }
            catch (Exception ex)
            {
                // Xử lý lỗi (đưa ra thông báo hoặc ghi log)
                return false; // Cập nhật thất bại
            }
        }

        //------------------------------------------------------------------Lãi Xuất-----------------------------------------------------

        public List<LaiSuat> GetNameLaiSuat()
        {
            FirebaseResponse response = client.Get("LaiSuat");
            if(response != null)
            {
                Dictionary<string, LaiSuat> data = JsonConvert.DeserializeObject<Dictionary<string, LaiSuat>>(response.Body);
                return new List<LaiSuat>(data.Values);
            }
            return null;
        }

        public LaiSuat GetLaiSuat(string key)
        {
            FirebaseResponse response = client.Get("LaiSuat");
            if (response != null)
            {
                Dictionary<string, LaiSuat> data = JsonConvert.DeserializeObject<Dictionary<string, LaiSuat>>(response.Body);
                return data.Values.FirstOrDefault(x => x.Key == key);  
            }
            return null;
        }

        public TaiKhoanLienKet GetTaiKhoanLienKetSTK(long stk)
        {
            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string,TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string,TaiKhoanLienKet>>(response.Body);
                return data.Values.FirstOrDefault(p => p.SoTaiKhoan == stk );
            }
            return null;
        }

        public void GuiTietKiem(GuiTietKiem guiTietKiem,TaiKhoanLienKet taiKhoanLien)
        {
            guiTietKiem.TaiKhoanTietKiem = long.Parse(TaiKhoanTietKiem());
            FirebaseResponse response = client.Push("GuiTietKiem",guiTietKiem);
            if (response != null)
            {
                // Phân tích chuỗi JSON để lấy key
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                string newKey = data["name"];
                double laixuat = TienLai(guiTietKiem.LaiSuatKey,guiTietKiem.TienGui);



                // Cập nhật đối tượng GuiTietKiem với Key mới
                FirebaseResponse updateResponse = client.Set("GuiTietKiem/" + newKey + "/Key", newKey);
                FirebaseResponse updatetienlai = client.Set("GuiTietKiem/" + newKey + "/TienLaiToiKy", laixuat);          
                if (updateResponse.StatusCode == HttpStatusCode.OK && updatetienlai.StatusCode == HttpStatusCode.OK)
                {
                    ChuyenTien(guiTietKiem.TienGui*(-1), taiKhoanLien.SoTaiKhoan);
                    Console.WriteLine("thanh công");
                }
                else
                {
                    Console.WriteLine(updateResponse.StatusCode);
                }
            }
        }

        public string TaiKhoanTietKiem()
        {
            string stk = AccountNumber("sotaikhoan");
            FirebaseResponse response = client.Get("GuiTietKiem");
            if (response != null && response.Body != "null")
            {
                Dictionary<string, TheNganHang> data = JsonConvert.DeserializeObject<Dictionary<string, TheNganHang>>(response.Body);
                if (data.ContainsKey(stk))
                {
                    return TaiKhoanTietKiem();
                }

            }
            return stk;



        }

      //  lấy tài khoản tiết kiệm
        public GuiTietKiem GetAccontSaveMoney(long stk)
        {
            FirebaseResponse response = client.Get("GuiTietKiem");
           
            if (response != null && response.Body != "null" )
            {
                Dictionary<string, GuiTietKiem> data = JsonConvert.DeserializeObject<Dictionary<string, GuiTietKiem>>(response.Body);
               
                return data.Values.FirstOrDefault(p => p.TaiKhoanTietKiem == stk || p.TaiKhoanNguon == stk);

            }
            return null;
        }

        //nạy tiền tiết kiệm
        public bool AdmitSaveMoney(double number, string key)
        {
            FirebaseResponse responsetien = client.Get("GuiTietKiem/" + key);
            if (responsetien != null)
            {
                JObject pss = JObject.Parse(responsetien.Body.ToString());
                double tien = (double)pss["TienGui"]+ number;
                double tienlai = TienLai((string)pss["LaiSuatKey"], tien);
                FirebaseResponse response = client.Set("GuiTietKiem/" + key + "/TienGui", tien);
                FirebaseResponse rptienlai = client.Set("GuiTietKiem/" + key + "/TienLaiToiKy",tienlai );
                if (response != null && response.Body != "null")
                {

                    return true;
                }
            }
          
            return false;
        }
       public double TienLai(string key,double tiengui)
        {
            FirebaseResponse response = client.Get("LaiSuat/"+key);
            if(response != null && response.Body != "null")
            {
                JObject pss = JObject.Parse(response.Body.ToString());
                string str = (string)pss["KyHan"];              
                string[] parts = str.Split(' ');
                string numberPart = parts[0];

                return tiengui * double.Parse(numberPart) / 12 * (double)pss["TiLe"] / 100;
            }
           return 0;
        }
       
        //lưu giao dịch
        public bool LichSuGD(string key,GiaoDich giaoDich)
        {
            FirebaseResponse response = client.Push("GiaoDich", giaoDich);
            if (response != null)
            {
                // Phân tích chuỗi JSON để lấy key
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                string newKey = data["name"];



                // Cập nhật đối tượng GuiTietKiem với Key mới
                FirebaseResponse updateResponse = client.Set("GiaoDich/" + newKey + "/Key", newKey);
                if (updateResponse != null)
                {
                    return true;
                }
            }
            return false;
        }
    }

}

