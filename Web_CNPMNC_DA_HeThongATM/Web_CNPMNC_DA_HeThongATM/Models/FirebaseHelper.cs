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

            FirebaseResponse response = client.Get("LichSuGiaoDich");
            //Dictionary<string, LichSuGiaoDich> data = response.ResultAs<Dictionary<string, LichSuGiaoDich>>();

            //totalTransaction = data.Values.Count;

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


        //tìm kiếm thẻ theo cccd


        //------------------------------------------------------------------Chức năng trong dịch vụ-----------------------------------------------------//

        public string GetAccount(long Value )
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
            else
            {
                // Số tài khoản không tồn tại
                return false; // Rút tiền thất bại
            }
        }

        //-----------------------------------------------------Chức năng chuyển tiền-----------------------------------------------------//
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





        //-------------------------------------------------------------QUản lý loại thẻ-----------------------------------------------//
        /* public List<LoaiThe> GetTypesCards()
         {
             List<LoaiThe> dsLoaiThe = new List<LoaiThe>();
             FirebaseResponse response = client.Get("LoaiTheNganHang");
             List<LoaiThe> data = response.ResultAs<List<LoaiThe>>();
             dsLoaiThe.AddRange(data);
             return dsLoaiThe;
         }*/
        public List<LoaiThe> GetTypesCards()
        {
            List<LoaiThe> dsLoaiThe = new List<LoaiThe>();
            FirebaseResponse response = client.Get("LoaiTheNganHang");
            Dictionary<string, LoaiThe> data = response.ResultAs<Dictionary<string, LoaiThe>>();
            dsLoaiThe = new List<LoaiThe>(data.Values);
            return dsLoaiThe;
        }

        //tạo Loại thẻ
        public void InsertTypesCards(LoaiTheViewModel typescards)
        {
            try
            {
                FirebaseResponse response = client.Set("LoaiTheNganHang/" + typescards.MaLoaiTNH, typescards);
                if (response != null)
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

                    if (deleteResponse != null)
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

        // update lãi suất
        public void UpdateLaiSuatByKey(string key, LaiSuatViewModel updatedLaiSuat)
        {
            try
            {
                FirebaseResponse response = client.Update("LaiSuat/" + key, updatedLaiSuat);
                if (response != null)
                {
                    
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        //delete lãi suất
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
                
                throw;
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






        //public bool TrangThaiTk(long SoTaiKhoan, int tinhTrangTaiKhoan)
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



    }
}

