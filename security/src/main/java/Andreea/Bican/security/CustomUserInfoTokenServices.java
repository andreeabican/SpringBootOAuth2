package Andreea.Bican.security;

import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import Andreea.Bican.model.User;
import Andreea.Bican.service.UserService;

public class CustomUserInfoTokenServices extends UserInfoTokenServices
{
    private String provider;
    private UserService userService;

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId, UserService userService) {
        super(userInfoEndpointUrl, clientId);
        this.userService = userService;
    }

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId, UserService userService, String provider) {
        super(userInfoEndpointUrl, clientId);
        this.userService = userService;
        this.provider = provider;
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException {
        //TODO: before parsing the token, for proper spring-boot treatment of authorities, we should call super.setAuthoritiesExtractor() here and pass in an authorities extractor customized to properly set our own set of user Authorities based on the database table holding our internal UserAuthorities for the UUID of this user. Then these will be available through spring annotation for protecting certain URI endpoints or Authentication.getAuthorities(). But for now, we can just UUID checking for authorities through the developed web-api services.
        OAuth2Authentication auth = super.loadAuthentication(accessToken);
        Map<String, Object> userDetails = getUserDetails(auth);
        String userEmail = getUserEmail(userDetails);
        User foundUser = userService.getUser(userEmail);
        if (foundUser != null) {
            userDetails.put("email", userEmail);
            userDetails.put("username", getUserName(userDetails));
            userDetails.put("cbioportal_uuid", foundUser.getUuid());
        } else {
            throw new UsernameNotFoundException("Unknown user: " + userEmail);
        }
        return auth;
    }

    private String getUserName(Map<String, Object> userDetails) {
        if(provider.equals("google")) {
            return (String) userDetails.get("displayName");
        }
        if(provider.equals("facebook")) {
            return (String) userDetails.get("name");
        }
        return "unknown";
    }

    private String getUserEmail(Map<String, Object> userDetails) {
        if (provider.equals("google")) {
            List<Map<String, Object>> emails =
                    (List<Map<String, Object>>) userDetails.get("emails");
            for (Map<String, Object> emailAccount : emails) {
                if (emailAccount.containsKey("value")) {
                    return (String)emailAccount.get("value");
                }
            }
        }
        if(provider.equals("facebook")){
            return (String) userDetails.get("email");
        }
        return "unknown";
    }

    private Map<String, Object> getUserDetails(OAuth2Authentication auth) {
        return (Map<String, Object>) auth.getUserAuthentication().getDetails();
    }
}
