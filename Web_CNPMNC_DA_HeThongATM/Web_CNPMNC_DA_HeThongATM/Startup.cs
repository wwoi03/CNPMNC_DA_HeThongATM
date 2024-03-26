using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.Extensions.DependencyInjection;
using Web_CNPMNC_DA_HeThongATM.Models;

namespace Web_CNPMNC_DA_HeThongATM
{
    public class Startup
    {
        public void ConfigureServices(IServiceCollection services)
        {
            // Đối tượng cấu hình Firebase
            IFirebaseConfig firebaseConfig = new FirebaseConfig
            {
                AuthSecret = "086JcgQrRLjvg3lubA1YY9GlAvks4VrYTCeWJJy6",
                BasePath = "https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/"
            };

            // Đăng ký IFirebaseClient
            IFirebaseClient firebaseClient = new FireSharp.FirebaseClient(firebaseConfig);
            services.AddSingleton<IFirebaseClient>(firebaseClient);

            // Đăng ký FirebaseHelper
            services.AddSingleton<FirebaseHelper>();
        }
    }
}
