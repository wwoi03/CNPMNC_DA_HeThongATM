using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using UniqueIdGenerator;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using System.Security.Permissions;
using Web_CNPMNC_DA_HeThongATM.Controllers;



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


        //lấy tên  khách hàng qua id
        public string GetNameCustomerbyid(string values)
        {
            FirebaseResponse response = client.Get("KhachHang");
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
            return false;
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

            FirebaseResponse response = client.Get("GiaoDich");
            Dictionary<string, GiaoDich> data = response.ResultAs<Dictionary<string, GiaoDich>>();

            totalTransaction = data.Values.Count;

            //totalTransaction = data.Values.Count;

            return totalTransaction;
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

        //---------------------------------------------------------------NHÂN VIÊN--------------------------------------------------------
        //LẤY DANH SÁCH NHÂN VIÊN
        public List<NhanVien> GetStaffs()
        {
            FirebaseResponse response = client.Get("NhanVien");
            Dictionary<string, NhanVien> data = response.ResultAs<Dictionary<string, NhanVien>>();
            List<NhanVien> staffs = data.Values.ToList();
            return staffs;
        }
        //LẤY DANH SÁCH NHÂN VIÊN LẪN KEY
        public Dictionary<string, NhanVien> GetStaffsWithKey()
        {
            FirebaseResponse response = client.Get("NhanVien");
            Dictionary<string, NhanVien> data = response.ResultAs<Dictionary<string, NhanVien>>();
            return data;
        }

        // TẠO TÀI KHOẢN NHÂN VIÊN
        public void CreateStaff(NhanVien nhanVien)
        {
            //FirebaseResponse response = client.Push("NhanVien", nhanVien);

            PushResponse response = client.Push("NhanVien", nhanVien);
            string newKey = response.Result.name;

            // Gán key vào trường Key của đối tượng NhanVien
            nhanVien.Key = newKey;

            // Cập nhật dữ liệu Nhân viên với key trong Firebase
            SetResponse setResponse = client.Set("NhanVien/" + newKey, nhanVien);
        }

        //UPDATE TÀI KHOẢN NHÂN VIÊN
        public void UpdateStaff(NhanVien editedNhanVien)
        {
            // Gửi yêu cầu cập nhật nhân viên tới Firebase bằng cách sử dụng key
            SetResponse setResponse = client.Set("NhanVien/" + editedNhanVien.Key, editedNhanVien);
        }

        //--------------------------------------------------------------------------THẺ NGÂN HÀNG ----------------------------------------------------------------------------------
        //tạo số thẻ ngân hàng
        private string GenerateCIF()
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

        //lấy tên loại thẻ ngân hàng
        /*public string GetNameTypeCard(string values)
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
        //Lấy tên tài khoản bằng Số tài khoản
        public TaiKhoanLienKet GetAccountbyid(long sotaikhoan)
        {
            FirebaseResponse response = client.Get("TaiKhoanLienKet");
            if (response != null)
            {
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);

                var a = data.Values.FirstOrDefault(c => c.SoTaiKhoan == sotaikhoan);
                if (a != null)
                {
                    return a;
                }
            }
            return null;
        }


        //Chức năng Rút tiền
        public bool RutTien(double soTien, long soTaiKhoan)
        {
            // Lấy thông tin tài khoản từ Firebase bằng số tài khoản
            string info = GetAccount(soTaiKhoan);

            if (info != null)
            {
                try
                {
                    // Lấy dữ liệu tài khoản từ Firebase
                    FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);
                    var accountData = response.ResultAs<TaiKhoanLienKetViewModel>();

                    // Kiểm tra xem số dư trong tài khoản có đủ để rút không
                    if (accountData.SoDu >= soTien)
                    {
                        // Trừ số tiền vào số dư
                        double SoDuMoi = accountData.SoDu - soTien;

                        // Cập nhật số dư trên Firebase
                        client.Set("TaiKhoanLienKet/" + info + "/SoDu", SoDuMoi);

                        return true; // Rút tiền thành công
                    }
                    else
                    {
                        // Số dư không đủ để rút
                        return false; // Rút tiền thất bại
                    }
                }
                catch (Exception ex)
                {
                    // Xử lý lỗi (đưa ra thông báo hoặc ghi log)
                    return false; // Rút tiền thất bại
                }
            }
            return "";
        }*/

        /*//tìm kiếm thẻ theo cccd/ id thẻ/số điện thoại
        public TheNganHang SearchCard(string searchValue)
        {
            string info = GetAccount(value);
            try
            {
                FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);

                // Thử tìm theo CCCD (Căn cước công dân)
                var card = data.Values.FirstOrDefault(c => c. == searchValue);



                // Nếu không tìm thấy theo CCCD, thử tìm theo ID thẻ)

                if (card == null)
                {

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);

            }
        }
        //chỉnh sửa loại thẻ
        // Edit an existing type of card
        internal LoaiThe GetMaLoaiTNH(long maLoaiTNH)
        {
            throw new NotImplementedException();
        }
        public void EditTypesCard(long MaLoaiTNH, LoaiTheViewModel updatedCard)
        {
            try
            {
                FirebaseResponse getResponse = client.Get("LoaiTheNganHang/" + MaLoaiTNH);
                LoaiTheViewModel existingCard = getResponse.ResultAs<LoaiTheViewModel>();

                if (existingCard != null)
                {

                    FirebaseResponse setResponse = client.Set("LoaiTheNganHang/" + MaLoaiTNH, existingCard);

                    if (setResponse != null)
                    {

                    }
                }
                else
                {
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        //xóa loại thẻ
        // Delete a type of card
        public void DeleteTypesCard(long MaLoaiTNH)
        {
            try
            {
                FirebaseResponse getResponse = client.Get("LoaiTheNganHang/" + MaLoaiTNH);
                if (getResponse != null && getResponse.ResultAs<LoaiTheViewModel>() != null)
                {
                    FirebaseResponse deleteResponse = client.Delete("LoaiTheNganHang/" + MaLoaiTNH);

            // Trả về null nếu không tìm thấy khách hàng
            return null;
        }*/

        //lấy thẻ theo id
        public TheNganHang GetTheNganHangById(long masothe)
        {
            List<LaiSuat> dsLaiSuat = new List<LaiSuat>();
            FirebaseResponse response = client.Get("LaiSuat");
            Dictionary<string, LaiSuat> data = response.ResultAs<Dictionary<string, LaiSuat>>();
            dsLaiSuat = new List<LaiSuat>(data.Values);
            return dsLaiSuat;
        }
      

        //tạo Lãi suất
        public void InsertLaiSuats(LaiSuatViewModel laiSuat)
        {
           
            try
            {
                FirebaseResponse response = client.Set("LaiSuat/" + laiSuat.Key, laiSuat);
                if (response != null)
                {

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);

            }
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
            catch (Exception ex)
            {
                
                throw;
            }
        }

        public string GetAccount(long Value)

        {

            FirebaseResponse response = client.Get("TaiKhoanLienKet");

            if (response != null)

            {

                Dictionary<string, TaiKhoanLienKetViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKetViewModel>>(response.Body);

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



        public bool NapTien(double soTien, long value)

        {

            string info = GetAccount(value);

            try

            {

                FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);



                // Lấy dữ liệu tài khoản từ Firebase

                var accountData = response.ResultAs<TaiKhoanLienKetViewModel>();



                //// Cộng số tiền vào số dư

                //accountData.SoDu += soTien;

                Double SoDuHientai = accountData.SoDu + soTien;





                // Cập nhật số dư trên Firebase

                client.Set("TaiKhoanLienKet/" + info + "/SoDu", SoDuHientai);



                return true; // Cập nhật thành công

            }

            catch (Exception ex)

            {

                // Xử lý lỗi (đưa ra thông báo hoặc ghi log)

                return false; // Cập nhật thất bại

            }

        }

        //đừng xóa em đang sửa
        //public string GetAccount(string Value)
        //{
        //    string info = GetAccount(SoTaiKhoan);
        //    try
        //    {
        //        FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);

        //        if (response != null && response.ResultAs<TaiKhoanLienKetViewModel>() != null)
        //        {
        //            var accountData = response.ResultAs<TaiKhoanLienKetViewModel>();

        //            // Kiểm tra giá trị tinhTrangTaiKhoan
        //            if (tinhTrangTaiKhoan == 1)
        //            {
        //                // Mở tài khoản
        //                accountData.TinhTrangTaiKhoan = 1;
        //            }
        //            else if (tinhTrangTaiKhoan == 2)
        //            {
        //                // Khóa tài khoản
        //                accountData.TinhTrangTaiKhoan = 2;
        //            }
        //            else
        //            {
        //                // Trạng thái không hợp lệ
        //                return false;
        //            }

        //            // Cập nhật trạng thái tài khoản
        //            client.Set("TaiKhoanLienKet/" + info, accountData);
        //            return true; // Cập nhật thành công
        //        }
        //        else
        //        {
        //            return false; // Tài khoản không tồn tại
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        return false; // Cập nhật thất bại
        //    }
        //}

        //----------------------------------------------------- Chức năng -----------------------------------------------------
        // Lấy danh sách chức năng
        public List<ChucNang> GetListService()
        {
            FirebaseResponse response = client.Get("ChucNang");
            Dictionary<string, ChucNang> data = response.ResultAs<Dictionary<string, ChucNang>>();

            List<ChucNang> listService = new List<ChucNang>(data.Values);

            return listService;
        }

        // Thêm chức năng
        public void CreateService(ChucNang chucNang)
        {
            PushResponse response = client.Push("ChucNang", chucNang);
            string newKey = response.Result.name;

            // Gán key vào trường Key của đối tượng Chức năng
            chucNang.Key = newKey;

            // Cập nhật dữ liệu Nhân viên với key trong Firebase
            SetResponse setResponse = client.Set("ChucNang/" + newKey, chucNang);
        }

        // lấy chức năng bằng key
        public ChucNang GetServiceByKey(string key)
        {
            FirebaseResponse response = client.Get("ChucNang/" + key);

            return response.ResultAs<ChucNang>();
        }

        // Sửa chức năng
        public void UpdateService(ChucNang chucNang)
        {
            SetResponse setResponse = client.Set("ChucNang/" + chucNang.Key, chucNang);
        }
    }
}