package Andreea.Bican;

import Andreea.Bican.impl.CurrentContextServiceImpl;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CurrentContextServiceImpl securityCurrentContext;

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
       /* if(principal != null) {
            securityCurrentContext.setCurrentUser();
        }*/
        return principal;
    }
}