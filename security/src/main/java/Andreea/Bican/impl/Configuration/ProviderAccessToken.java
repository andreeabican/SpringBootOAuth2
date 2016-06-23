package Andreea.Bican.impl.Configuration;

import Andreea.Bican.CurrentUser;
import Andreea.Bican.impl.IAccessToken;
import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookAccessToken;
import Andreea.Bican.impl.Oauth2.GoogleAuthentication.GoogleAccessToken;

/**
 * Created by andre on 24.06.2016.
 */
public class ProviderAccessToken {

    public IAccessToken getProviderAccessToken()
    {
        if(CurrentUser.getUser() == null){
            return null;
        }
        if(CurrentUser.getUser().getProvider().equals("Facebook")){
            return new FacebookAccessToken();
        }else{
            return new GoogleAccessToken();
        }
    }
}
