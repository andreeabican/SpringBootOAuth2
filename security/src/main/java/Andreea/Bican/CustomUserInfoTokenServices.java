package Andreea.Bican;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.*;

/**
 * Created by grossb on 6/2/16.
 */
public class CustomUserInfoTokenServices extends UserInfoTokenServices
{
    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId)
    {
        super(userInfoEndpointUrl, clientId);
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException
    {
        OAuth2Authentication auth = super.loadAuthentication(accessToken);
        if (userIsKnown(getUserDetails(auth))) {
            // add UUID - should come from DB
            String UUID = "24e1c30e-1c30-49ee-9dab-64494a14335b";
            getUserDetails(auth).put("cbioportal_uuid", UUID);
            getUserDetails(auth).put("email", getUserEmail(getUserDetails(auth)));
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

    private String getUserEmail(Map<String, Object> userDetails)
    {
        if (userDetails.containsKey("emails")) {
            List<Map<String, Object>> emails =
                    (List<Map<String, Object>>) userDetails.get("emails");
            for (Map<String, Object> emailAccount : emails) {
                if (emailAccount.containsKey("value")) {
                    return (String)emailAccount.get("value");
                }
            }
        }
        return "unknown";
    }

    private Map<String, Object> getUserDetails(OAuth2Authentication auth)
    {
        return (Map<String, Object>) auth.getUserAuthentication().getDetails();
    }
}
