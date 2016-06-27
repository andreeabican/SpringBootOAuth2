package Andreea.Bican.WebApiViews;

import Andreea.Bican.User;
import Andreea.Bican.impl.Configuration.ProviderAccessToken;
import Andreea.Bican.impl.CurrentContextServiceImpl;
import Andreea.Bican.impl.IAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class GreetingView {

    ProviderAccessToken providerAccessToken;

    @Autowired
    CurrentContextServiceImpl currentContextService;

    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String danceClasses(@RequestHeader(value = "token")String token) throws Exception {
        providerAccessToken = new ProviderAccessToken();
        IAccessToken accessToken = providerAccessToken.getProviderAccessToken(token);

        User user = currentContextService.getCurrentUser(token);
        if(accessToken.checkToken(token)){
            return "Hello, " + user.getName();
        }
        else{
            return "You are not logged";
        }
    }

}
