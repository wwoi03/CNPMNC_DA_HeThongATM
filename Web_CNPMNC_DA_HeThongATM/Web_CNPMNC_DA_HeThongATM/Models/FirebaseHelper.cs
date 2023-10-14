﻿using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Web_CNPMNC_DA_HeThongATM.Models.ClassModel;
using Web_CNPMNC_DA_HeThongATM.Models.ViewModel;
using System.Net.Http;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Web;
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
        //Lấy danh sách khách hàng
        public List<KhachHang> GetCustomers()
        {
            List<KhachHang> dsKhachHang = new List<KhachHang>();
            FirebaseResponse response = client.Get("KhachHang");
            Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
            dsKhachHang = new List<KhachHang>(data.Values);
            return dsKhachHang;
        }
       // Lấy số lượng khách hàng theo năm hiện tại và theo từng tháng
        public Dictionary<string, int> GetQuantityCustomerByMonth(int year)
        {
            Dictionary<string, int> quantityCustomerByMonth = new Dictionary<string, int>();
            FirebaseResponse response = client.Get("KhachHang");
            Dictionary<string, KhachHang> data = response.ResultAs<Dictionary<string, KhachHang>>();
           //tính số lượng khách hàng theo từng tháng trong năm "year"
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
            SetResponse setResponse = client.Set("NhanVien/"+editedNhanVien.Key, editedNhanVien);
        }

        //XÓA TÀI KHOẢN NHÂN VIÊN
        public void DeleteStaff(string accKey)
        {
            // Gửi yêu cầu xóa loại tài khoản từ Firebase bằng cách sử dụng key
            FirebaseResponse setResponse = client.Delete("NhanVien/" + accKey);
        }
      //---------------------------------------------------------------------------------------------------------------------------------------
     












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
            SetResponse setResponse = client.Set("LoaiTaiKhoan/"+editedAcc.Key, editedAcc);
        }

        //XÓA LOẠI TÀI KHOẢN
        public void DeleteAcc(string accKey)
        {
            // Gửi yêu cầu xóa loại tài khoản từ Firebase bằng cách sử dụng key
           FirebaseResponse setResponse = client.Delete("LoaiTaiKhoan/" + accKey);
        }
      //-------------------------------------------------------------------------------------------------------------------------------




















































































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
    }
}

