package Andreea.Bican.impl;

import Andreea.Bican.CurrentUsersService;
import Andreea.Bican.CurrentContextService;
import Andreea.Bican.User;
import Andreea.Bican.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

/**
 * Created by andre on 23.06.2016.
 */
@Service
public class CurrentContextServiceImpl implements CurrentContextService {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUsersService currentUsersService;

    public  void setCurrentUser()
    {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();

            Map<String, Object> userDetails = (Map<String, Object>) userAuthentication.getDetails();
                currentUsersService.logUser(getUserToken(userDetails), getUserEmail(userDetails));
        }
    }

    public User getCurrentUser(String token)
    {
        return currentUsersService.getLoggedUserByToken(token);
    }

   /* public User getUserFromRepository()
    {
        return userService.getUser( CurrentUser.getUser().getEmail());
    }*/
    public User getUserFromCurrentContext()
    {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();

            Map<String, Object> userDetails = (Map<String, Object>) userAuthentication.getDetails();
            return getUser(userDetails);
        }
        return null;
    }

    public User getUser(Map<String, Object> userDetails)
    {
        User user = new User();
        user.setEmail(getUserEmail(userDetails));
        user.setName(getUserName(userDetails));
        user.setId(getUserId(userDetails));
        user.setProvider(getUserProvider(userDetails));
        user.setToken(getUserToken(userDetails));
        return user;
    }

    public String getUserEmail(Map<String, Object> userDetails)
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

    public String getUserName(Map<String, Object> userDetails)
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

    public String getUserProvider(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("provider")){
            return null;
        }
        return (String) userDetails.get("provider");
    }

    public String getUserToken(Map<String, Object> userDetails)
    {
        if(!userDetails.containsKey("token")){
            return null;
        }
        return (String) userDetails.get("token");
    }

    public String getUserId(Map<String, Object> userDetails)
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
