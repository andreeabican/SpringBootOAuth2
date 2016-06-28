package Andreea.Bican;

import java.util.Map;

/**
 * Created by andre on 28.06.2016.
 */
public interface CurrentContextService {
    void setCurrentUser();
    User getCurrentUser(String token);
    User getUser(Map<String, Object> userDetails);
    String getUserEmail(Map<String, Object> userDetails);
    String getUserName(Map<String, Object> userDetails);
    String getUserProvider(Map<String, Object> userDetails);
    String getUserToken(Map<String, Object> userDetails);
    String getUserId(Map<String, Object> userDetails);
    User getUserFromCurrentContext();
}
