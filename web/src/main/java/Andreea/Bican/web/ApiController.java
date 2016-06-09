package Andreea.Bican.web;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.security.Principal;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import Andreea.Bican.model.User;
import Andreea.Bican.model.Authorities;
import Andreea.Bican.service.UserService;
import Andreea.Bican.service.AuthoritiesService;

@RestController
public class ApiController
{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
        return principal;
    }

    @RequestMapping("/danceclasses")
    public String danceClasses(Principal principal) {
        //example of checking authentication with a User lookup
        User currentUser = getCurrentUser(null);
        if (currentUser == null) {
            return "You need to authenticate";
        }
        Set<String> userAuthorities = currentUser.getAuthorities();
        if(!userAuthorities.contains("STUDENT")) {
            return "You currently don't have a dance class";
        }
        return "Welcome to your dance class";
    }

    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            //if caller does not pass in principal, try to get it from context holder
            principal = SecurityContextHolder.getContext().getAuthentication();
        }
        if (!(principal instanceof OAuth2Authentication)) {
            return null;
        }
        OAuth2Authentication oAuth2authentication = (OAuth2Authentication)principal;
        Authentication userAuthentication = oAuth2authentication.getUserAuthentication();
        if (!userAuthentication.isAuthenticated()) {
            return null;
        }
        Map<String,Object> userDetails = (Map<String,Object>)userAuthentication.getDetails();
        if (userDetails == null) {
            return null;
        }
        String userEmail = (String)userDetails.get("email");
        if (userEmail == null) {
            userEmail = "null";
        }
        return userService.getUser(userEmail);
    }

    @RequestMapping("/greeting")
    public String greeting(Principal principal) {
        //example of checking authentication without a User lookup
        if (principal == null) {
            return "No user authentication information available; please login";
        }
        OAuth2Authentication oAuth2authentication = (OAuth2Authentication)principal;
        Authentication userAuthentication = oAuth2authentication.getUserAuthentication();
        if (!userAuthentication.isAuthenticated()) {
            return "You have not yet authenticated. Please login";
        }
        Map<String,Object> userDetails = (Map<String,Object>)userAuthentication.getDetails();
        if (userDetails == null) {
            return "server error: user details have not been set";
        }
        String userUuid = (String)userDetails.get("cbioportal_uuid");
        if (userUuid == null) {
            userUuid = "null";
        }
        List<Authorities> uuidAuthorities = authoritiesService.getAuthorities(userUuid);
        String authoritiesString = "";
        for (Authorities authorities : uuidAuthorities) {
            authoritiesString = authoritiesString + " " + authorities.getAuthority();
        }
        return "User has uuid: " + userUuid + ", authorities: " + authoritiesString;
    }
}
