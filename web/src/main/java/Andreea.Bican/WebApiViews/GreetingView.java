package Andreea.Bican.WebApiViews;

import Andreea.Bican.TokenService;
import Andreea.Bican.User;
import Andreea.Bican.UserService;
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

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String danceClasses(@RequestHeader(value = "token")String token) throws Exception {

        String email = tokenService.getEmailFromGoogleAccessToken(token);
        User user = userService.getUser(email);
        if(user != null){
            return "Hello, " + user.getName();
        }
        else{
            return "You are not logged";
        }
    }

}
