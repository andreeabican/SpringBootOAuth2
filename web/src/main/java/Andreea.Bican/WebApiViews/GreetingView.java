package Andreea.Bican.WebApiViews;

import Andreea.Bican.TokenService;
import Andreea.Bican.User;
import Andreea.Bican.impl.CurrentContextServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class GreetingView {

    @Autowired
    @Qualifier("listOfUsers")
    HashMap<String, User> loggedUsersList;

    @Autowired
    CurrentContextServiceImpl currentContextService;

    @Autowired
    TokenService tokenService;

    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String danceClasses(@RequestHeader(value = "token")String token) throws Exception {

        User user = currentContextService.getCurrentUser(token);
        if(tokenService.checkToken(token)){
            return "Hello, " + user.getName();
        }
        else{
            return "You are not logged";
        }
    }

}
