using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System.Configuration;
using Web_CNPMNC_DA_HeThongATM.Designpattern.Factorymethod;

namespace Web_CNPMNC_DA_HeThongATM
{
    public class Startup
    {
        public IConfiguration Configuration { get; }

        // Được gọi bởi runtime khi môi trường đang chạy ứng dụng
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        // Trong phương thức ConfigureServices của lớp Startup
        public void ConfigureServices(IServiceCollection services)
        {
            // Đăng ký các dịch vụ cần thiết khác
            services.AddControllersWithViews();

            // Đăng ký dịch vụ ILaiSuatFactory
            services.AddScoped<ILaiSuatFactory, ILaiSuatFactory>(); // Thay MyLaiSuatFactory bằng implementation của ILaiSuatFactory của bạn
        }


        // Cấu hình middleware
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller=Home}/{action=Index}/{id?}");
            });
        }
    }
}
