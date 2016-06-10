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
import Andreea.Bican.model.Class;
import Andreea.Bican.model.Student;
import Andreea.Bican.service.UserService;
import Andreea.Bican.service.AuthoritiesService;
import Andreea.Bican.service.ClassService;
import Andreea.Bican.service.StudentService;

@RestController
public class ApiController
{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

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

    @RequestMapping("/class")
    public String classs(Principal principal) {
        //no user lookup needed -- eveybody can see a class listing
        Class exampleClass = classService.getClass(3);
        if (exampleClass == null) {
            return "null";
        }
        return "class_id: " + Integer.toString(exampleClass.getId()) +
                "<br>name: " + exampleClass.getName() +
                "<br>style: " + exampleClass.getStyle().getName() +
                "<br>description: " + exampleClass.getDescription();
    }

    @RequestMapping("/classes")
    public String classes(Principal principal) {
        //no user lookup needed -- eveybody can see class listings
        List<Class> classes = classService.getClasses();
        if (classes == null) {
            return "null";
        }
        String outputString = "";
        for (Class classs : classes) {
            outputString = outputString +
                    "class_id: " + Integer.toString(classs.getId()) +
                    "<br>name: " + classs.getName() +
                    "<br>style: " + classs.getStyle().getName() +
                    "<br>description: " + classs.getDescription() +
                    "<br>&nbsp;<br>";
        }
        return outputString;
    }

    @RequestMapping("/student")
    public String student(Principal principal) {
        User currentUser = getCurrentUser(null);
        if (currentUser == null) {
            return "You need to authenticate";
        }
        Set<String> userAuthorities = currentUser.getAuthorities();
        if(!userAuthorities.contains("ADMIN")) {
            return "You don't have authority to look up students";
        }
        Student exampleStudent = studentService.getStudent(3);
        if (exampleStudent == null) {
            return "null";
        }
        return "student_id: " + Integer.toString(exampleStudent.getId()) + "\r\nname: " + exampleStudent.getName();
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
}
