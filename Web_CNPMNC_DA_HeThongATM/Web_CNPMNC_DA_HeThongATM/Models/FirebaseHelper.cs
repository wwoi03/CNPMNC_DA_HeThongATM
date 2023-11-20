using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using UniqueIdGenerator;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using Web_CNPMNC_DA_HeThongATM.Controllers;
using System.Globalization;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using System.Security.Principal;
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
            }
            return "";
        }

        //lấy tên  khách hàng qua id
        public async Task<string> GetNameCustomerbyid2(string values)
        {
            FirebaseResponse response = await client.GetAsync("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);
                if (data.TryGetValue(values, out KhachHang khachHang))
                {
                    return khachHang.TenKH;
                }
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

        public KhachHang SearchCustomer(string searchCustomer)
        {
            FirebaseResponse response = client.Get("KhachHang");
            if (response != null)
            {
                Dictionary<string, KhachHang> data = JsonConvert.DeserializeObject<Dictionary<string, KhachHang>>(response.Body);

                var card = data.Values.FirstOrDefault(c => c.CCCD == searchCustomer);

                return card;
            }

            // Trả về null nếu không tìm thấy khách hàng
            return null;
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

        //LẤY DANH SÁCH LOAI TAI KHOAN
        public List<LoaiTaiKhoan> GetAcc()
        {
            FirebaseResponse response = client.Get("LoaiTaiKhoan");
            Dictionary<string, LoaiTaiKhoan> data = response.ResultAs<Dictionary<string, LoaiTaiKhoan>>();
            List<LoaiTaiKhoan> loaiTaiKhoans = data.Values.ToList();
            return loaiTaiKhoans;
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

        //XÓA TÀI KHOẢN NHÂN VIÊN
        public void DeleteStaff(string accKey)
        {
            // Gửi yêu cầu xóa loại tài khoản từ Firebase bằng cách sử dụng key
            FirebaseResponse setResponse = client.Delete("NhanVien/" + accKey);
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

        //tự động tạo mã số thẻ
        private string AccountNumber(string request)
        {
            DateTime now = DateTime.Now;
            Random random = new Random();
            int r = random.Next(0, 99);
            // Tạo số CIF sử dụng thời gian và GUID ngẫu nhiên
            string cif = $"{now:HHmmss}{r.ToString("D2")}{r.ToString("D2")}";
            if (request.Equals("masothe"))
                return "909090" + cif;
            return "072" + cif;

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
                FirebaseResponse updateResponse = client.Set("TheNganHang/" + newKey + "/Key", newKey);

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
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                string newKey = data["name"];

                // Cập nhật đối tượng TheNganHang với MaKHKey mới
                FirebaseResponse updateResponse = client.Set("TheNganHang/" + newKey + "/Key", newKey);

                Console.WriteLine("thành công");
            }
            else { Console.WriteLine("thất bại"); }
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

        public bool NapTien(double soTien, long value,long stk)
        {
            string info = GetAccount(value);
            try
            {
                FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);

                // Lấy dữ liệu tài khoản từ Firebase
                var accountData = response.ResultAs<TaiKhoanLienKet>();

                Double SoDuHientai = accountData.SoDu + soTien;

                // Cập nhật số dư trên Firebase
                client.Set("TaiKhoanLienKet/" + info + "/SoDu", SoDuHientai);

                //lưu lich sử giao dịch nạp tiền
				GiaoDich giaoDich = new GiaoDich
				{
					Key = "",
					GioGiaoDich = DateTime.Now.ToString("HH:mm:ss"),
					NgayGiaoDich = DateTime.Now.ToString("dd/MM/yyy"),
					LoaiGiaoDichKey = "1",
					NoiDungChuyenKhoan = "Nạp tiền vào tài khoản",
					PhiGiaoDich = 0,
					SoDuLucGui = accountData.SoDu,
					SoDuLucNhan = SoDuHientai,
					SoTienGiaoDich = soTien,
					TaiKhoanNguon = stk,
				};
                double g = giaoDich.SoDuLucNhan;
				LichSuGD(giaoDich.LoaiGiaoDichKey, giaoDich);
				return true; // Cập nhật thành công

            }
            catch (Exception ex)
            {
                return false; // Cập nhật thất bại
            }
        }

        //----------------------------------------------------- Dịch vụ khách hàng -----------------------------------------------------
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
            else
            {
                // Số tài khoản không tồn tại
                return false; // Rút tiền thất bại
            }
        }

        // chuyển tiền
        public bool ChuyenTien(double soTien, long taiKhoanNguoiChuyen, long taiKhoanNguoiNhan)
        {
            try
            {
                string infoNguoiChuyen = GetAccount(taiKhoanNguoiChuyen);
                string infoNguoiNhan = GetAccount(taiKhoanNguoiNhan);

                FirebaseResponse responseNguoiChuyen = client.Get("TaiKhoanLienKet/" + infoNguoiChuyen);
                FirebaseResponse responseNguoiNhan = client.Get("TaiKhoanLienKet/" + infoNguoiNhan);

                if (responseNguoiChuyen != null && responseNguoiNhan != null)
                {
                    var nguoiChuyen = responseNguoiChuyen.ResultAs<TaiKhoanLienKetViewModel>();
                    var nguoiNhan = responseNguoiNhan.ResultAs<TaiKhoanLienKetViewModel>();

                    // Xử lý chuyển tiền
                    nguoiChuyen.SoDu -= soTien; // Giả sử trừ số tiền từ tài khoản người chuyển
                    nguoiNhan.SoDu += soTien;   // Giả sử cộng số tiền vào tài khoản người nhận

                    // Cập nhật lại thông tin tài khoản
                    client.Set("TaiKhoanLienKet/" + infoNguoiChuyen, nguoiChuyen);
                    client.Set("TaiKhoanLienKet/" + infoNguoiNhan, nguoiNhan);

                    return true; // Chuyển tiền thành công
                }
                else
                {
                    return false; // Tài khoản không tồn tại
                }
            }
            catch (Exception ex)
            {
                return false; // Xử lý lỗi nếu có
            }
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

        //-------------------------------------------------QUản lý lãi suất---------------------------------------------------------//

        //Danh sách LÃI SUẤT
        public List<LaiSuat> GetLaiSuats()
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

        public LaiSuatViewModel GetLaiSuatByKey(string key)
        {
            try
            {
                FirebaseResponse response = client.Get("LaiSuat/" + key);
                if (response == null || string.IsNullOrEmpty(response.Body))
                {
                    return null;
                }

                LaiSuatViewModel laiSuat = JsonConvert.DeserializeObject<LaiSuatViewModel>(response.Body);
                return laiSuat;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return null;
            }
        }

        //xóa
        public void DeleteLaiSuat(string key)
        {
            try
            {
                var path = "LaiSuat/" + key;
                client.Delete(path);
                // Xóa thành công
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                // Xử lý lỗi nếu cần
            }
        }

        //Thêm phương thức sau vào lớp FirebaseHelper
        public void UpdateLaiSuatByKey(string key, LaiSuatViewModel updatedLaiSuat)
        {
            try
            {
                var path = "LaiSuat/" + key;
                client.Update(path, updatedLaiSuat);
                // Cập nhật thành công
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                // Xử lý lỗi nếu cần
            }
        }

        //-------------------------------------------------mở khóa tài khoản ---------------------------------------------------------//
        public bool TrangThaiTk(long SoTaiKhoan, int tinhTrangTaiKhoan)
        {
            string info = GetAccount(SoTaiKhoan);

            try
            {
                FirebaseResponse response = client.Get("TaiKhoanLienKet/" + info);

                if (response != null && response.ResultAs<TaiKhoanLienKetViewModel>() != null)
                {
                    var accountData = response.ResultAs<TaiKhoanLienKetViewModel>();

                    // Kiểm tra giá trị tinhTrangTaiKhoan
                    if (tinhTrangTaiKhoan == 0 || tinhTrangTaiKhoan == 1)
                    {
                        // Mở/khóa tài khoản
                        accountData.TinhTrangTaiKhoan = tinhTrangTaiKhoan;

                        // Cập nhật trạng thái tài khoản
                        client.Set("TaiKhoanLienKet/" + info, accountData);
                        return true; // Cập nhật thành công
                    }
                }

                return false; // Tài khoản không tồn tại hoặc giá trị tinhTrangTaiKhoan không hợp lệ
            }
            catch (Exception ex)
            {
                // Xử lý lỗi (đưa ra thông báo hoặc ghi log)
                return false; // Cập nhật thất bại
            }
        }

        public string GetAccountStatusMessage(int tinhTrangTaiKhoan)
        {
            switch (tinhTrangTaiKhoan)
            {
                case 0:
                    return "Tài khoản đang hoạt động";
                case 1:
                    return "Tài khoản đang bị khóa";
                default:
                    return "Trạng thái không xác định";
            }
        }

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

                    GiaoDichViewModel ls = new GiaoDichViewModel(item, status, date, tenKH, tenKH2, sodu);
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
        // Chuc vu**********************************************************************
        public List<ChucVu> GetListRole()
        {
            List<ChucVu> roles;
            FirebaseResponse response = client.Get("ChucVu");
            Dictionary<string, ChucVu> data = response.ResultAs<Dictionary<string, ChucVu>>();
            roles = new List<ChucVu>(data.Values);
            return roles.OrderBy(p => p.TenChucVu).ToList(); ;
        }
        public async Task<bool> checkRole(ChucVu chucVu)
        {
            try
            {

                FirebaseResponse response = await client.GetAsync("ChucVu");

                if (response == null || response.Body == "null")
                {
                    return false; // Node không tồn tại hoặc trống
                }

                Dictionary<string, ChucVu> data = response.ResultAs<Dictionary<string, ChucVu>>();

                if (data != null && data.Values.Any(item => item.TenChucVu == chucVu.TenChucVu))
                {
                    return true; // Chuc vu tồn tại trong danh sách
                }

                return false; // Chuc vu không tồn tại trong danh sách
            }
            catch (Exception ex)
            {
                // Xử lý lỗi nếu có
                Console.WriteLine(ex.Message);
                return false;
            }
        }
        // Them chuc vu
        public void AddRole(ChucVu chucVu)
        {

            // Thực hiện Push để lấy key mới
            PushResponse response = client.Push("ChucVu", chucVu);

            if (response != null && response.Result != null)
            {
                // Lấy key mới
                string newKey = response.Result.name;

                // Thiết lập key mới cho đối tượng ChucVu
                chucVu.Key = newKey;

                // Thực hiện Set để thêm dữ liệu vào Firebase
                client.Set("ChucVu/" + newKey, chucVu);


            }
            else
            {
                // Xử lý khi có lỗi trong quá trình Push
                Console.WriteLine("Error while pushing data to Firebase.");
            }
        }
        public ChucVu getRolebyKey(string key)
        {
            FirebaseResponse response = client.Get("ChucVu");
            if (response != null)
            {
                Dictionary<string, ChucVu> data = JsonConvert.DeserializeObject<Dictionary<string, ChucVu>>(response.Body);
                ChucVu chucVu = data[key];
                if (chucVu != null)
                    return chucVu;
            }
            return null;
        }

        public void EditRole(ChucVu chucVu)
        {

            FirebaseResponse response = client.Get("ChucVu");
            if (response != null)
            {
                client.Set("ChucVu/" + chucVu.Key, chucVu);
            }
        }
        public void DeleteRole(string key)
        {

            FirebaseResponse response = client.Get("ChucVu");
            if (response != null)
            {
                client.Delete("ChucVu/" + key);
            }
        }

        //-------------------------------------------------LOẠI TÀI KHOẢN---------------------------------------------------------------

        // TẠO LOẠI TÀI KHOẢN
        public void CreateAccount(LoaiTaiKhoan loaiTaiKhoan)
        {
            //FirebaseResponse response = client.Push("NhanVien", nhanVien);

            PushResponse response = client.Push("LoaiTaiKhoan", loaiTaiKhoan);
            string newKey = response.Result.name;

            // Gán key vào trường Key của đối tượng NhanVien
            loaiTaiKhoan.Key = newKey;

            // Cập nhật dữ liệu Nhân viên với key trong Firebase
            SetResponse setResponse = client.Set("LoaiTaiKhoan/" + newKey, loaiTaiKhoan);
        }

        //LẤY DANH SÁCH lOẠI TÀI KHOẢN LẪN KEY
        public Dictionary<string, LoaiTaiKhoan> GetAccWithKey()
        {
            FirebaseResponse response = client.Get("LoaiTaiKhoan");
            Dictionary<string, LoaiTaiKhoan> data = response.ResultAs<Dictionary<string, LoaiTaiKhoan>>();
            return data;
        }

        //UPDATE LOẠI TÀI KHOẢN 
        public void UpdateAcc(LoaiTaiKhoan editedAcc)
        {
            // Gửi yêu cầu cập nhật nhân viên tới Firebase bằng cách sử dụng key
            SetResponse setResponse = client.Set("LoaiTaiKhoan/" + editedAcc.Key, editedAcc);
        }

        //XÓA LOẠI TÀI KHOẢN
        public void DeleteAcc(string accKey)
        {
            // Gửi yêu cầu xóa loại tài khoản từ Firebase bằng cách sử dụng key
            FirebaseResponse setResponse = client.Delete("LoaiTaiKhoan/" + accKey);
        }

        //------------------------------------------------------------------Gửi tiết kiệm-----------------------------------------------------

        public List<LaiSuat> GetNameLaiSuat()
        {
            FirebaseResponse response = client.Get("LaiSuat");
            if (response != null)
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
                Dictionary<string, TaiKhoanLienKet> data = JsonConvert.DeserializeObject<Dictionary<string, TaiKhoanLienKet>>(response.Body);
                return data.Values.FirstOrDefault(p => p.SoTaiKhoan == stk);
            }
            return null;
        }

        public void GuiTietKiem(GuiTietKiem guiTietKiem, TaiKhoanLienKet taiKhoanLien)
        {
            guiTietKiem.TaiKhoanTietKiem = long.Parse(TaiKhoanTietKiem());
            FirebaseResponse response = client.Push("GuiTietKiem", guiTietKiem);
            if (response != null)
            {
                // Phân tích chuỗi JSON để lấy key
                var data = JsonConvert.DeserializeObject<Dictionary<string, string>>(response.Body);
                string newKey = data["name"];
                double laixuat = TienLai(guiTietKiem.LaiSuatKey, guiTietKiem.TienGui);



                // Cập nhật đối tượng GuiTietKiem với Key mới
                FirebaseResponse updateResponse = client.Set("GuiTietKiem/" + newKey + "/Key", newKey);
                FirebaseResponse updatetienlai = client.Set("GuiTietKiem/" + newKey + "/TienLaiToiKy", laixuat);
                if (updateResponse.StatusCode == HttpStatusCode.OK && updatetienlai.StatusCode == HttpStatusCode.OK)
                {
                    ChuyenTien(guiTietKiem.TienGui * (-1), taiKhoanLien.SoTaiKhoan);
                    Console.WriteLine("thanh công");
                }
                else
                {
                    Console.WriteLine(updateResponse.StatusCode);
                }
            }
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

            if (response != null && response.Body != "null")
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
                double tien = (double)pss["TienGui"] + number;
                double tienlai = TienLai((string)pss["LaiSuatKey"], tien);
                FirebaseResponse response = client.Set("GuiTietKiem/" + key + "/TienGui", tien);
              //  FirebaseResponse rptienlai = client.Set("GuiTietKiem/" + key + "/TienLaiToiKy", tienlai);
                if (response != null && response.Body != "null")
                {

                    return true;
                }
            }

            return false;
        }
        public double TienLai(string key, double tiengui)
        {
            FirebaseResponse response = client.Get("LaiSuat/" + key);
            if (response != null && response.Body != "null")
            {
                JObject pss = JObject.Parse(response.Body.ToString());
                string str = (string)pss["KyHan"];
                string[] parts = str.Split(' ');
                string numberPart = parts[0];

                return tiengui * (double)pss["TiLe"] / 100;
            }
            return 0;
        }

        //lưu giao dịch
        public bool LichSuGD(string key, GiaoDich giaoDich)
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
        //tất toán tiết kiệm
        public void TatToanTietKiem(long sotklk,string keytktk, double tien)
        {
            var data = new 
            {
                TienLaiToiKy = 0,

                TienGui = 0,
            };
            FirebaseResponse response = client.Update("GuiTietKiem/"+keytktk,data);
            if (response != null)
            {
                ChuyenTien(tien, sotklk);
            }
           
        }
       
        //-------------------------------------------------Đăt lịch hẹn---------------------------------------------------------------
        public List<DatLichHen> GetAppointent()
        {
            List<DatLichHen> dslichhen = new List<DatLichHen>();
            FirebaseResponse response = client.Get("DatLichHen");
            Dictionary<string, DatLichHen> data = response.ResultAs<Dictionary<string, DatLichHen>>();
            dslichhen = new List<DatLichHen>(data.Values);
            return dslichhen;
        }
        public void InsertAppointment(DatLichHenViewModel datLichHen)
        {
            //FirebaseResponse response = client.Push("NhanVien", nhanVien);

            PushResponse response = client.Push("DatLichHen", datLichHen);
            string newKey = response.Result.name;

            // Gán key vào trường Key của đối tượng NhanVien
            datLichHen.Key = newKey;

            // Cập nhật dữ liệu Nhân viên với key trong Firebase
            SetResponse setResponse = client.Set("DatLichHen/" + newKey, datLichHen);
        }

        public DatLichHen GetLichHenByKey(string key)
        {
            try
            {
                FirebaseResponse response = client.Get("DatLichHen/" + key);
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

        public string GetStatus(long Value)

        {

            FirebaseResponse response = client.Get("DatLichHen");

            if (response != null)

            {

                Dictionary<string, DatLichHenViewModel> data = JsonConvert.DeserializeObject<Dictionary<string, DatLichHenViewModel>>(response.Body);

                var statusData = data.Select(entry => entry.Key).ToList();

                if (statusData != null)

                {

                    return string.Join(",", statusData);

                }

            }

            else

            {

                Console.WriteLine(response.StatusCode);

            }

            return null;



        }
        public bool TrangThai(int trangThai, string key)
        {
            try
            {
                FirebaseResponse response = client.Set("DatLichHen/" + key + "/TrangThai", trangThai);
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
        public DatLichHen GetAppointmentbyKey(string values)
        {

            FirebaseResponse response = client.Get("DatLichHen");
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
		public bool LichSuGD(string key, GiaoDich giaoDich)
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