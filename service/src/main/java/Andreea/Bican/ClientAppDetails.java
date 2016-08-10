package Andreea.Bican;

/**
 * Created by andre on 22.07.2016.
 */
public class ClientAppDetails {
    private static String googleClientId = "";

    private static String facebookClientId = "";

    private static String facebookClientSecret = "";

    private static String googleClientSecret = "";

    private static String serviceAccountId = "";

    private static String p12FileName = "";

    private static String serviceAccountUser = "";

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
