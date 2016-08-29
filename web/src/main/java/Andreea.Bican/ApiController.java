package Andreea.Bican;

import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by grossb on 5/31/16.
 */
@RestController
public class ApiController
{
    @RequestMapping(value="/user")
    public String user() throws IOException, ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        return principal;
    }
}