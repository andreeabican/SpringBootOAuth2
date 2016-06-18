package Andreea.Bican.impl.Oauth2.FacebookAuthentication;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

/**
 * Created by andre on 13.06.2016.
 */
public class FacebookCustomUserInfoTokenService extends UserInfoTokenServices {

    public FacebookCustomUserInfoTokenService(String userInfoEndpointUrl, String clientId)
    {
        super(userInfoEndpointUrl, clientId);
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException
    {
        OAuth2Authentication auth = super.loadAuthentication(accessToken);
        if (userIsKnown(getUserDetails(auth))) {
            getUserDetails(auth).put("userId", getUserId(getUserDetails(auth)));
            getUserDetails(auth).put("userEmail", getUserEmail(getUserDetails(auth)));
            getUserDetails(auth).put("userName", getUserName(getUserDetails(auth)));
        }
        else {
            throw new UsernameNotFoundException("Unknown user: " + getUserEmail(getUserDetails(auth)));
        }
        return auth;
    }

    private boolean userIsKnown(Map<String, Object> userDetails)
    {
        String email = getUserEmail(userDetails);
        if (email.equals("unknown")) return false;
        // look in db here
        return true;
    }

    private String getUserName(Map<String, Object> userDetails)
    {
        if(userDetails.containsKey("name")){
            return (String) userDetails.get("name");
        }
        return "unknown";
    }

    private String getUserEmail(Map<String, Object> userDetails)
    {
        if(userDetails.containsKey("email")){
            return (String) userDetails.get("email");
        }
        return "unknown";
    }

    private String getUserId(Map<String, Object> userDetails)
    {
        if(userDetails.containsKey("id"))
        {
            return (String)userDetails.get("id");
        }
        return "unknown";
    }

    private Map<String, Object> getUserDetails(OAuth2Authentication auth)
    {
        return (Map<String, Object>) auth.getUserAuthentication().getDetails();
    }
}
