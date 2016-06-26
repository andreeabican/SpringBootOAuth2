package Andreea.Bican;

import Andreea.Bican.impl.Configuration.ProviderAccessToken;
import Andreea.Bican.impl.IAccessToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class LoggedUserView {

    ProviderAccessToken providerAccessToken;

    @RequestMapping(value="/loggedusersview", method = RequestMethod.GET)
    public String danceClasses(@RequestHeader(value = "token")String token) throws Exception {
        providerAccessToken = new ProviderAccessToken();
        IAccessToken accessToken = providerAccessToken.getProviderAccessToken();

        if(accessToken.checkToken(token)){
            return "You are logged, " + CurrentUser.getUser().getName();
        }
        else{
            return "You are not logged";
        }
    }

}
