package Andreea.Bican.impl.Configuration;

import Andreea.Bican.User;
import Andreea.Bican.impl.IAccessToken;
import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookAccessToken;
import Andreea.Bican.impl.Oauth2.GoogleAuthentication.GoogleAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;

/**
 * Created by andre on 24.06.2016.
 */
public class ProviderAccessToken {

    @Autowired
    @Qualifier("listOfUsers")
    HashMap<String, User> loggedUsersList;

    public IAccessToken getProviderAccessToken(String token)
    {
        if(loggedUsersList.containsKey(token)){
            String provider = loggedUsersList.get(token).getProvider();
            if(provider.equals("Facebook")){
                return new FacebookAccessToken();
            }else if(provider.equals("Google")){
                return new GoogleAccessToken();
            }
        }
        return null;
    }
}
