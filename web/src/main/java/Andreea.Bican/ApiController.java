package Andreea.Bican;

import com.sun.javafx.geom.transform.Identity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by grossb on 5/31/16.
 */
@RestController
public class ApiController
{
    UserRepository userRepository = new UserRepository();

    @RequestMapping("/user")
    public Principal user(Principal identity) throws IOException, ParseException {
        if(identity == null) {
            return null;
        }else{
            return identity;
        }
    }

    @RequestMapping(value="/danceclasses", method = RequestMethod.GET)
    public String danceClasses(@RequestHeader("token")String token) {
            if(token.equals(CurrentUser.accessToken))
                return token;
        if(CurrentUser.isAuthenticated()){
            if(CurrentUser.getUser().getAuthority()){
                return "Welcome to your dance class";
            }else{
                return "You currently don't have a dance class";
            }
        }else{
            return "You need to authenticate";
        }
    }


    public User getLoggedUser(Identity identity) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        User user = new User();
        try{
            JSONObject jsonObject = (JSONObject)parser.parse(identity.toString());

            JSONObject userAuthJSON = (JSONObject)jsonObject.get("userAuthentication");
            JSONObject detailsJSON = (JSONObject)userAuthJSON.get("details");
            String userName = (String)detailsJSON.get("userName");
            user.setUserName(userName);

            String email = (String) detailsJSON.get("userEmail");
            user.setEmail(email);

            String id = (String) detailsJSON.get("userId");
            user.setId(id);
            return user;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/greeting")
    public String greeting() {
        if(CurrentUser.isAuthenticated()) {
            return "Hello user " + CurrentUser.getUser().getUserName() + ".\n"
                    + "Your email is: " + CurrentUser.getUser().getEmail() + ".\n"
                    + "Your UUID is " + CurrentUser.getUser().getId();
        }else{
            return "Hello, annonymous";
        }
    }
}