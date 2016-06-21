package Andreea.Bican;

/**
 * Created by andre on 27.05.2016.
 */
public class CurrentUser{

    private static User currentUser;

    private static String accessToken;

    private static String provider;

    private static String userName;

    public void login(){  currentUser = new User();  }

    public static void logout(){ currentUser = null; }

    public static void setUserName(String username){ userName = username;  }

    public static String getUserName(){ return userName;  }

    public static void setAccessToken(String token){ accessToken = token;}

    public static String getAccessToken(){ return accessToken; }

    public static void setProvider(String authProvider){ provider = authProvider; }

    public static String getProvider(){ return provider; }

    public static boolean isAuthenticated(){
        if(currentUser != null) {
            return true;
        }else{
            return false;
        }
    }
}