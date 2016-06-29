package Andreea.Bican;

/**
 * Created by andre on 26.06.2016.
 */
public interface CurrentUsersService {

    void logUser(String token, String id);

    User getLoggedUserByToken(String token);

    boolean checkLoggedUser(String token);

    void deleteLoggedUserByToken(String token);

    void deleteLoggedUserByEmail(String email);

    void updateTokenForLoggedUser(String newToken, String email);

    User getUserFromRepository(String email);

    User getLoggedUserByEmail(String email);

    void addUserFromRepository(String token, User user);

    void addUserFromCurrentContext(String token);
}
