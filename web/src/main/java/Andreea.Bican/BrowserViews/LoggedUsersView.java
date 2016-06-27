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
                    "\"\"\n email" + user.getValue().getEmail() +
                    "\"\"\n";
        }
        if(loggedUsersList.isEmpty()){
            return "No users";
        }else{
            return outputString;
        }
    }
}
