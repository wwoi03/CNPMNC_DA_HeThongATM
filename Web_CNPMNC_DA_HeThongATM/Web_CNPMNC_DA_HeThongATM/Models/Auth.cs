using Firebase.Auth;
using Firebase.Auth.Providers;
using Firebase.Auth.Repository;
namespace Web_CNPMNC_DA_HeThongATM.Models
{
    public class Auth
    {
        public static FirebaseAuthClient client;
        
        public Auth()
        {
            var config = new FirebaseAuthConfig
            {
                ApiKey = "AIzaSyDZpeSXI3qwhFZq8Vs6iqVRFZ2lnrjoPPk",
                AuthDomain = "noreply@systematm-aea2c.firebaseapp.com",
                

            };
            client = new FirebaseAuthClient(config);
        }

        
    }

}
