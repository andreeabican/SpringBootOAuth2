package Andreea.Bican.impl.Configuration;

import Andreea.Bican.CurrentUser;
import Andreea.Bican.User;
import Andreea.Bican.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.Map;

/**
 * Created by andre on 23.06.2016.
 */
public class SecurityCurrentContext {

    @Autowired
    private UserService userService;

    public  void setCurrentUser(Principal principal)
    {
        if (principal == null) {
            // if there is no user logged in
            CurrentUser.setUser(null);
        }else {
            OAuth2Authentication oAuth2authentication = (OAuth2Authentication) principal;
            Authentication userAuthentication = oAuth2authentication.getUserAuthentication();
            if (!userAuthentication.isAuthenticated()) {
                CurrentUser.setUser(null);
            }
            Map<String, Object> userDetails = (Map<String, Object>) userAuthentication.getDetails();
            if (userDetails == null) {
                CurrentUser.setUser(null);
            } else {
                CurrentUser.setUser(getUser(userDetails));
            }
        }
    }

    public User getUserFromRepository()
    {
        return userService.getUser(CurrentUser.getUser().getEmail());
    }

    private User getUser(Map<String, Object> userDetails)
    {
        User user = new User();
        user.setEmail(getUserEmail(userDetails));
        user.setName(getUserName(userDetails));
        user.setId(getUserId(userDetails));
        user.setProvider(getUserProvider(userDetails));
        user.setToken(getUserToken(userDetails));
        return user;
    }

    private String getUserEmail(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("userEmail")){
            return null;
        }

        String userEmail = (String) userDetails.get("userEmail");
        if(userEmail.equals("unknown")){
            return null;
        }
        return userEmail;
    }

    private String getUserName(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("userName")){
            return null;
        }
        String userName = (String)userDetails.get("userName");
        if(userName.equals("unknown")){
            return null;
        }
        return userName;
    }

    private String getUserProvider(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("provider")){
            return null;
        }
        return (String) userDetails.get("provider");
    }

    private String getUserToken(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("token")){
            return null;
        }
        return (String) userDetails.get("token");
    }

    private String getUserId(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("userId")){
            return null;
        }
        String userId = (String) userDetails.get("userId");
        if(userId.equals("unknown")){
            return null;
        }
        return userId;
    }
}
