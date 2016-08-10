package Andreea.Bican;

/**
 * Created by andre on 22.07.2016.
 */
public class ClientAppDetails {
    private static String googleClientId = "565779346670-4hqpp1qbqa45go8pue5ncgirsk4rnc1o.apps.googleusercontent.com";

    private static String facebookClientId = "1726962864239113";

    private static String facebookClientSecret = "43fdfab2ebdee4978bf7090a02817414";

    private static String googleClientSecret = "ulAV2AnP1vkezZ1ORN7D9pA6";

    private static String serviceAccountId = "andreea@oauth2-1314.iam.gserviceaccount.com";

    private static String p12FileName = "OAuth2-89e8a661d295.p12";

    private static String serviceAccountUser = "andreeasanzianabican@gmail.com";

    public static void setServiceAccountId(String serviceAccountId){ ClientAppDetails.serviceAccountId = serviceAccountId; }

    public static void setP12FileName(String p12FileName){ ClientAppDetails.p12FileName = p12FileName;}

    public static void setServiceAccountUser(String serviceAccountUser) { ClientAppDetails.serviceAccountUser = serviceAccountUser;  }

    public static String getGoogleClientId(){
        return googleClientId;
    }

    public static String getGoogleClientSecret(){
        return googleClientSecret;
    }

    public static String getFacebookClientId(){
        return facebookClientId;
    }

    public static String getFacebookClientSecret(){
        return facebookClientSecret;
    }

    public static String getServiceAccountId(){ return serviceAccountId; }

    public static String getP12FileName(){ return p12FileName; }

    public static String getServiceAccountUser(){ return serviceAccountUser;}

}
