package Andreea.Bican;

/**
 * Created by andre on 26.06.2016.
 */
public interface CurrentUsersService {

    void addLoggedUser(String token, String id);
    User getLoggedUserByToken(String token);
    boolean checkLoggedUser(String token);
    void deleteLoggedUser(String token);
}
