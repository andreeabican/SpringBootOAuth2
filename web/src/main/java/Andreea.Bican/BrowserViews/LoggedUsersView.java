package Andreea.Bican.BrowserViews;

import Andreea.Bican.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class LoggedUsersView {

    @Autowired
    @Qualifier("listOfUsers")
    HashMap<String, User> loggedUsersList;

    @RequestMapping("/loggedusers")
    public String greeting() {
        String outputString = "";

        for(Map.Entry<String, User> user : loggedUsersList.entrySet()) {
            outputString += user.getValue().getName() +
                    "<br /> email: " + user.getValue().getEmail() +
                    "<br /> id: " + user.getValue().getId() +
                    "<br /> provider: " + user.getValue().getProvider() +
                    "<br /> token: " + user.getKey() + "<br /><br />";
        }
        if(loggedUsersList.isEmpty()){
            return "No users";
        }else{
            return outputString;
        }
    }
}
