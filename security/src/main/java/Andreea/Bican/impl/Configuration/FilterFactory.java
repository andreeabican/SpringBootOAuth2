package Andreea.Bican.impl.Configuration;

import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookFilter;
import Andreea.Bican.impl.Oauth2.GoogleAuthentication.GoogleFilter;
import Andreea.Bican.impl.Oauth2.ProviderFilter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

/**
 * Created by andre on 13.06.2016.
 */
public class FilterFactory{

    public ProviderFilter createFilter(String provider, OAuth2ClientContext oAuth2ClientContext) {
        if(provider.equals("facebook")){
            return new FacebookFilter();
        }else if(provider.equals("google"))
        {
            return new GoogleFilter();
        }
        return null;
    }
}
