package Andreea.Bican.WebApiViews;

import Andreea.Bican.Student;
import Andreea.Bican.StudentService;
import Andreea.Bican.User;
import Andreea.Bican.impl.CurrentContextServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
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

    private CurrentContextServiceImpl securityCurrentContext;

    @RequestMapping("/student")
    public String student(Principal principal, @RequestHeader(value = "token")String token) {
        securityCurrentContext = new CurrentContextServiceImpl();
        User currentUser = securityCurrentContext.getCurrentUser(token);
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
