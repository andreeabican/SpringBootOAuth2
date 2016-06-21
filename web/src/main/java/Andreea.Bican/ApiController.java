package Andreea.Bican;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by grossb on 5/31/16.
 */
@RestController
public class ApiController
{

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
        if(principal == null) {
            CurrentUser.logout();
            return null;
        }else{
            return principal;
        }
    }

}