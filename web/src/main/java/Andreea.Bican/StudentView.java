package Andreea.Bican;

import Andreea.Bican.impl.Configuration.SecurityCurrentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

/**
 * Created by andre on 24.06.2016.
 */

@RestController
public class StudentView {

    @Autowired
    private StudentService studentService;

    private SecurityCurrentContext securityCurrentContext;

    @RequestMapping("/student")
    public String student(Principal principal) {
        securityCurrentContext = new SecurityCurrentContext();
        User currentUser = securityCurrentContext.getCurrentUser();
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
}
