package Andreea.Bican;

import java.security.Principal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by grossb on 5/31/16.
 */
@RestController
public class ApiController
{
    UserRepository userRepository = new UserRepository();

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
        if(principal == null) {
            //CurrentUser.logout();
            return null;
        }else{
            /*
            convertPrincipalObjectToJSON(principal);
            User loggedUser = getLoggedUser();
            User user = userRepository.getUser(loggedUser.getId());
            if(user != null) {
                CurrentUser.login(user);
            }else{
                loggedUser.setAuthority(false);
                CurrentUser.login(loggedUser);
            }
            */
            return principal;
        }
    }

    @RequestMapping("/danceclasses")
    public String danceClasses() {
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

    public String convertPrincipalObjectToJSON(Principal principal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("principal.json"), principal);
        return mapper.writeValueAsString(principal);
    }

    public User getLoggedUser() throws IOException, ParseException {

        FileReader reader  = new FileReader("principal.json");
        JSONParser parser = new JSONParser();
        User user = new User();
        try{
            JSONObject jsonObject = (JSONObject)parser.parse(reader);

            JSONObject userAuthJSON = (JSONObject)jsonObject.get("userAuthentication");
            JSONObject detailsJSON = (JSONObject)userAuthJSON.get("details");
            String userName = (String)detailsJSON.get("displayName");
            user.setUserName(userName);

            JSONArray emailsJSON = (JSONArray)detailsJSON.get("emails");
            JSONObject emailJSON = (JSONObject) emailsJSON.get(0);
            String email = (String) emailJSON.get("value");
            user.setEmail(email);

            String id = (String) detailsJSON.get("id");
            user.setId(id);
            user.setUuid();
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
