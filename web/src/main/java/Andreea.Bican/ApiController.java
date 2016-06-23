package Andreea.Bican;

import Andreea.Bican.impl.Configuration.SecurityCurrentContext;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by grossb on 5/31/16.
 */
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

    @Autowired
    private StudentListService studentListService;

    @Autowired
    private StudentListMembershipService studentListMembershipService;

    SecurityCurrentContext securityCurrentContext;

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
        securityCurrentContext = new SecurityCurrentContext();
        securityCurrentContext.setCurrentUser(principal);
        return principal;
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

    @RequestMapping("/studentlists")
    public String studentList(Principal principal) {
        //TODO: add appropriate user authority checking here (Authorized user has authority which is in set of authorities for list of lists)
        int hardcodedClassId = 3;
        List<StudentList> studentLists = studentListService.getStudentLists(hardcodedClassId); //hardcoded example
        if (studentLists == null) {
            return "null";
        }
        String outputString = "student lists which have class id " + Integer.toString(hardcodedClassId) + ":";
        for (StudentList studentList : studentLists) {
            outputString = outputString +
                    "<br>&nbsp;" + Integer.toString(studentList.getStudentListId()) + ", (required authority : in";
            //Iterator<String> authorityIterator = studentList.getAuthorities().iterator();
                outputString = outputString + " " + studentList.getAuthorities();
            outputString = outputString + ")";
        }
        return outputString;
    }

    @RequestMapping("/student")
    public String student(Principal principal) {
        User currentUser = getCurrentUser(null);
        if (principal == null) {
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
        String userEmail = (String)userDetails.get("userEmail");
        if (userEmail == null) {
            userEmail = "null";
        }
        return userService.getUser(userEmail);
    }
}