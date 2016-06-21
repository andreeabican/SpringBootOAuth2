package Andreea.Bican;

/**
 * Created by andre on 27.05.2016.
 */
public class CurrentUser{
    private static User currentUser;
    public static String accessToken;

    public static User getUser() { return currentUser;}

    public static void login(User user){ currentUser = user; }

    public static void logout(){ currentUser = null; }

    public static boolean isAuthenticated(){
        if(currentUser != null) {
            return true;
        }else{
            return false;
        }
    }
}