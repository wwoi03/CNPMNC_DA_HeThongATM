namespace Web_CNPMNC_DA_HeThongATM.Models

{
    public class NhanVien
    {
        public String name { get; set; }
        public String birth { get; set; }
        public String street { get; set; }
        public String email { get; set; }
        public String branch { get; set; }
        public String position { get; set; }
        public String password { get; set; }
        
        public NhanVien()
        {

        }

        public NhanVien(String name, String birth, String street, String email, String branch, String position, String password)
        {
            this.name = name;
            this.birth = birth;     
            this.street = street;
            this.email = email;
            this.branch = branch;
            this.position = position;
            this.password = password;
        }
    }
}
