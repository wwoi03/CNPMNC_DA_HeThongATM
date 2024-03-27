using Web_CNPMNC_DA_HeThongATM.Controllers;
using Web_CNPMNC_DA_HeThongATM.Controllers.Factory_method;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

// Kích hoạt Session
builder.Services.AddSession();

// Đăng ký dịch vụ của bạn ở đây
builder.Services.AddSingleton<IInterestRateFactory, SimpleInterestRateFactory>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

// Sử dụng session
app.UseSession();

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Staff}/{action=Login}/{id?}");

app.Run();
