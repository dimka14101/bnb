using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(BNB.Startup))]
namespace BNB
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
