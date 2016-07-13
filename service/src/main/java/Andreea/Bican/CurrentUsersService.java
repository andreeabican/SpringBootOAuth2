package Andreea.Bican;

/**
 * Created by andre on 26.06.2016.
 */
public interface CurrentUsersService {

    void logUserForBrowserSessions(String token, String email);

    void logUserForWebApiSessions(String token, String email, String provider);

    void addUserAndSessionId(String email, String sessionId);

    User getLoggedUserByToken(String token);

    String getEmailBySessionId(String sessionId);

    boolean checkLoggedUser(String token);

    void deleteLoggedUserByToken(String token);

    void deleteLoggedUserByEmail(String email);

    void updateTokenForLoggedUser(String newToken, String email);

    User getUserFromRepository(String email);

    User getLoggedUserByEmail(String email);

    void addUserFromRepository(String token, User user);

    void addUserFromCurrentContext(String token);
}
